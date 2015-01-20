package walmart;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * @author darcio
 *
 */
@JsonSerialize
public class WalmartPath  implements Serializable{

	private static final long serialVersionUID = 4078601775574089412L;

	private String startLocation;
	private String endLocation;
	private Double distance;
	
	
	public String getStartLocation() {
		return startLocation;
	}
	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}
	public String getEndLocation() {
		return endLocation;
	}
	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	
	
}
