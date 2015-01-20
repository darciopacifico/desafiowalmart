package br.com.walmart.service;

import br.com.walmart.exception.WalmartException;
import br.com.walmart.vo.PathCost;



/**
 * Interface do serviço de calculo de custo do menor caminho
 * 
 * @author darcio
 *
 */
public interface ShortestpathCostService {

	/**
	 * Calcula valor monetário do menor caminho entre A e B, considerando a autonomia do veículo e o valor do litro do combustível.
	 * @param nomeMapa 
	 * @param autonomia
	 * @param valorCombustivel
	 * @param locationA
	 * @param locationB
	 * @return
	 * @throws WalmartException
	 */
	PathCost shortestpathCost(String nomeMapa, Float autonomia, Float valorCombustivel, String locationA, String locationB) throws WalmartException;
	
}
