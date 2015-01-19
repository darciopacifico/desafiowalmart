package walmart;

import org.neo4j.graphdb.RelationshipType;

public enum EnumConnectionType implements RelationshipType { 

	STREET(0.7f), ROAD, FREEWAY(1.2f), FERRY, DIRT_ROAD(0.4f), BRIDGE(0.9f); 

	
	private Float coefRendimento=1f;
	
	private EnumConnectionType() {
	}

	private EnumConnectionType(Float coefConsumo) {
		this.coefRendimento = coefConsumo;
	}

	public Float getCoefRendimento() {
		return coefRendimento;
	}

	public void setCoefRendimento(Float coefConsumo) {
		this.coefRendimento = coefConsumo;
	}

	
	
};


