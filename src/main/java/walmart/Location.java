package walmart;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
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

	
	public Location() {
	}

	public Location(String name) {
		super();
		this.name = name;
	}

	@RelatedToVia
	@Fetch
	Set<Route> connections = new HashSet<Route>(5);

	/**
	 * 
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
	 * 
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

	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

}
