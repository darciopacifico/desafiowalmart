package br.com.walmart.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Abstrai a informaçao de caminho e custo.
 * 
 * Especialização de malha viária, contento a representação do melhor caminho entre dois pontos e seu custo.
 *  
 * @author darcio
 *
 */
@JsonSerialize
public class PathCost extends MalhaViaria{

	private static final long serialVersionUID = -2013939523835956254L;
	
	private Double totalCost;

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalWeight) {
		this.totalCost = totalWeight;
	}

}
