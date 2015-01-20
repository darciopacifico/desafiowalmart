package br.com.walmart.vo;

import org.neo4j.graphdb.RelationshipType;

/**
 * Tipifica os tipos de arestas para os grafos. Pode ser utilizado para otimizar o cálculo de consumo de combustível.
 * 
 * TODO: Otimizar cálculo de consumo de combustível, já que a autonomia do veículo pode ser afetada pelo tipo d estrada/terreno 
 * 
 * @author darcio
 *
 */
public enum EnumConnectionType implements RelationshipType { 

	STREET(0.6f), 
	ROAD(1f), 
	FREEWAY(1.2f), 
	FERRY(0f), 
	DIRT_ROAD(0.4f), 
	BRIDGE(0.8f); 

	
	private Float coefRendimento=1f;
	
	EnumConnectionType() {
	}

	EnumConnectionType(Float coefConsumo) {
		this.coefRendimento = coefConsumo;
	}

	public Float getCoefRendimento() {
		return coefRendimento;
	}

	public void setCoefRendimento(Float coefConsumo) {
		this.coefRendimento = coefConsumo;
	}

	
	
};


