package br.com.walmart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.walmart.exception.WalmartException;
import br.com.walmart.exception.WalmartRuntimeException;
import br.com.walmart.vo.CaminhoMalha;
import br.com.walmart.vo.PathCost;

/**
 * Implementacao padrao do servico de calculo de custo do menor caminho.
 * 
 * Implementação baseada no algoritmo Dijkstra, utilizando implementação do Neo4j
 * 
 * 
 * @author darcio
 * 
 */
@Component
public class ShortestpathCostServiceImpl implements ShortestpathCostService {

	private static final String NAME_ATTRIBUTE = "name";

	protected static final String WEIGHTED_ATTRIBUTE = "distance";

	@Autowired
	private GraphDatabase graphDatabase;

	/**
	 * Verifica o menor caminho entre dois pontos dados e calcula os gastos da viagem com combustivel
	 * 
	 * @param autonomia
	 * @param valorCombustivel
	 * @param locationA
	 * @param locationB
	 * @return
	 * @throws WalmartException
	 */
	@Transactional
	@Override
	public PathCost shortestpathCost(String nomeMapa, Float autonomia, Float valorCombustivel, String locationA, String locationB) throws WalmartException {

		// calcula custo em funcao de autonomia do veículo e preco do combustivel informados
		PathCost pathCost;
		Transaction tx = graphDatabase.beginTx();
		try {

			// busca os dois nós informados.
			List<Node> nodes = findNodes(locationA, locationB, nomeMapa);
			Node nodeA = nodes.get(0);
			Node nodeB = nodes.get(1);

			// não haverá distinção de tipo de estrada ou direção do relacionamento
			PathExpander<Object> expander = PathExpanders.allTypesAndDirections();

			// cria-se um pathFinder com algoritmo de Dijkstra, ponderado pelo atributo "distance"
			PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(expander, WEIGHTED_ATTRIBUTE);

			// executa a busca pelo menor caminho
			WeightedPath path = finder.findSinglePath(nodeA, nodeB);

			// trata caminho nao encontrado
			if (path == null) {
				throw new WalmartException("Nao ha caminho conhecido entre os pontos informados!");
			}

			pathCost = calculaCustoTotal(nomeMapa, autonomia, valorCombustivel, path);

			tx.success();

		} catch (Exception e) {

			tx.failure();

			throw new WalmartRuntimeException(e.getMessage(), e);

		}

		return pathCost;
	}

	/**
	 * Calcula valor da viagem e retorna toda a informacao num novo WeightedPath
	 * 
	 * @param nomeMapa
	 * @param autonomia
	 * @param valorCombustivel
	 * @param weightedPath
	 * @return
	 */
	private PathCost calculaCustoTotal(String nomeMapa, Float autonomia, Float valorCombustivel, WeightedPath weightedPath) {

		PathCost pathCost = new PathCost();

		// set nome do mapa
		pathCost.setNomeMapa(nomeMapa);

		// copia o caminho de WeightedPath para o bean PathCost
		copyPath(weightedPath, pathCost);

		// calcula custo total
		Double kms = weightedPath.weight();
		Double custoTotal = (kms / autonomia) * valorCombustivel;
		pathCost.setTotalCost(custoTotal);

		return pathCost;
	}

	/**
	 * Copia os registros de relationship para registros de path
	 * 
	 * @param fromPath
	 * @param toPathCost
	 */
	private void copyPath(WeightedPath fromPath, PathCost toPathCost) {
		Iterable<Relationship> relationships = fromPath.relationships();

		for (Relationship relationship : relationships) {

			CaminhoMalha caminhoMalha = new CaminhoMalha();

			caminhoMalha.setStartLocation((String) relationship.getStartNode().getProperty(NAME_ATTRIBUTE));
			caminhoMalha.setEndLocation((String) relationship.getEndNode().getProperty(NAME_ATTRIBUTE));
			caminhoMalha.setDistance((Double) relationship.getProperty(WEIGHTED_ATTRIBUTE));

			toPathCost.getCaminhos().add(caminhoMalha);

		}
	}

	/**
	 * Busca os dois nós informados. Lança RuntimeException, caso um dos nós não seja encontrado.
	 * 
	 * @param locationA
	 * @param locationB
	 * @param nomeMapa
	 * @return
	 */
	private List<Node> findNodes(String locationA, String locationB, String nomeMapa) {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		mapParam.put("nameA", locationA);
		mapParam.put("nameB", locationB);
		mapParam.put("mapa", nomeMapa);

		// TODO: Externalizar query
		Result<Map<String, Object>> result = graphDatabase.queryEngine().query("match (n) where (n.name={nameA} or n.name={nameB}) and n.mapa={mapa}  return DISTINCT n;", mapParam);

		List<Node> nodes = resultToList(result);

		// checa registros retornados

		if (nodes.size() != 2) {
			throw new WalmartRuntimeException("Dois e apenas dois nos deveriam ter sido encontrados para o calculo do menor caminho! (pontos encontrados " + nodes + ") ");
		}

		return nodes;
	}

	/**
	 * Transfere resultados para uma Lista.
	 * 
	 * @param result
	 * @return
	 */
	private List<Node> resultToList(Result<Map<String, Object>> result) {
		Iterator<Map<String, Object>> it = result.iterator();

		List<Node> nodes = new ArrayList<Node>();

		while (it.hasNext()) {
			Map<String, Object> obj = (Map<String, Object>) it.next();
			Node node = (Node) obj.get("n");
			nodes.add(node);
		}
		return nodes;
	}
}
