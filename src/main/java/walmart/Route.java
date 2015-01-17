package walmart;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.RelationshipType;
import org.springframework.data.neo4j.annotation.StartNode;



@RelationshipEntity(type="ROAD")
public class Route {
	
	@GraphId
	private Long id;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@RelationshipType
	String connectionType;

	@StartNode
	Location startLocation;

	@EndNode
	Location endLocation;
	
	
	Float distance;

	
	public Route() {
	}
	

	public Route(String connectionType, Location startLocation, Location endLocation, Float distance) {
		super();
		this.connectionType = connectionType;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.distance = distance;
	}


	public String getConnectionType() {
		return connectionType;
	}


	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
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


	public Float getDistance() {
		return distance;
	}


	public void setDistance(Float distance) {
		this.distance = distance;
	}
	
	
	
	
	
	
}
