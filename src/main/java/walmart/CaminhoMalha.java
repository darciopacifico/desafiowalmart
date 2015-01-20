package walmart;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
public class CaminhoMalha  implements Serializable{

	private static final long serialVersionUID = 7231543014974883852L;

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
