package walmart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Abstrai o cami
 * @author darcio
 *
 */
@JsonSerialize
public class PathCost implements Serializable {

	private static final long serialVersionUID = -2013939523835956254L;

	
	private Double totalCost;

	private List<WalmartPath> path = new ArrayList<>(10);

	
	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalWeight) {
		this.totalCost = totalWeight;
	}

	public List<WalmartPath> getPath() {
		return path;
	}

	public void setPath(List<WalmartPath> path) {
		this.path = path;
	}

	
	
	
	
}
