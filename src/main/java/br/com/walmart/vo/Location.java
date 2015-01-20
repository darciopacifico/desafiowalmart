package br.com.walmart.vo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Representa um vértice, ou local do mapa, nas malhas viárias.
 * 
 * O Atributo mapa, diferencia as diferentes malhas viárias contidas no sistema.
 * 
 * TODO: REVISAR SE ESTA É A MELHOR MANEIRA DE SEPARAR OS MAPAS. Achei interessante manter assim, para futuramente conectar os mapas, 
 * determinar por quais cidades o melhor caminho passará.
 * 
 * @author darcio
 */
@NodeEntity
@JsonSerialize
public class Location {

	@GraphId
	private Long id;

	private String name;
	
	private String mapa;

	@RelatedToVia
	@Fetch
	Set<Route> connections = new HashSet<Route>(5);
	
	
	public Location() {
	}

	public Location(String name) {
		super();
		this.name = name;
	}

	/**
	 * Conecta um location aa outro atraves de uma rota. Seta nesta rota a distância entre estes dois.
	 * @param otherLoc
	 * @param distance
	 * @param connectionType
	 * @return
	 */
	public Route connectTo(Location otherLoc, Double distance) {
		Route route = new Route(this,otherLoc, distance);
		this.connections.add(route);
		return route;
	}
	
	/**
	 * Conecta um location aa outro atraves de uma rota. Seta nesta rota a distância entre estes dois e o tipo de estrada
	 * @param otherLoc
	 * @param distance
	 * @param connectionType
	 * @return
	 */
	public Route connectTo(Location otherLoc, Double distance, EnumConnectionType type) {
		
		Route route = connectTo(otherLoc, distance);
		
		route.setType(type);
		
		return route;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMapa() {
		return mapa;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Route> getConnections() {
		return connections;
	}

	public void setConnections(Set<Route> connections) {
		this.connections = connections;
	}


	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

}
