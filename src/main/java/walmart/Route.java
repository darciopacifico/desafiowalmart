package walmart;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.RelationshipType;
import org.springframework.data.neo4j.annotation.StartNode;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Representa uma aresta do grafo. Este bean será persistido no Neo4J.
 * 
 * @author darcio
 */
@RelationshipEntity(type="ROAD")
@JsonSerialize
public class Route {
	
	@GraphId
	Long id;

	@RelationshipType
	EnumConnectionType type = EnumConnectionType.ROAD;

	@GraphProperty
	Double distance=0d;
	
	@StartNode
	@Fetch
	Location startLocation;

	@EndNode
	@Fetch
	Location endLocation;
	
	
	public Route() {
	}
	

	public Route(Location startLocation, Location endLocation, Double distance) {
		super();
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.distance = distance;
	}
	

	public Route(Location startLocation, Location endLocation, Double distance, EnumConnectionType type) {
		this(startLocation, endLocation, distance);
		this.type = type;
	}


	public Location getStartLocation() {
		return startLocation;
	}


	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}


	public Location getEndLocation() {
		return endLocation;
	}


	public void setEndLocation(Location endLocation) {
		this.endLocation = endLocation;
	}


	public Double getDistance() {
		return distance;
	}


	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	
	public EnumConnectionType getType() {
		return type;
	}


	public void setType(EnumConnectionType type) {
		this.type = type;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
	
}
