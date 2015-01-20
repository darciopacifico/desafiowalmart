package walmart;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * Representa a aresta ou vias (ruas, avenidas, estrada) dos grafos de malha viária. 
 * 
 * É composto por um ponto de partida, ponto de fim e distância entre estes dois.
 * 
 * @author darcio
 *
 */
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
