package br.com.walmart.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

import bsh.This;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Bean representando uma malha viaria do sistema.
 * 
 * Contém o nome do mapa, suas arestas e vértices.
 * 
 * O bean {@link #CaminhoMalha} contém as distâncias entre os vértices da malha.
 * 
 * @author darcio
 */
@JsonSerialize
public class MalhaViaria implements Serializable {

	private static final long serialVersionUID = -3829389330918058065L;

	private String nomeMapa;

	private List<CaminhoMalha> caminhos = new ArrayList<CaminhoMalha>(20);

	public String getNomeMapa() {
		return nomeMapa;
	}

	public void setNomeMapa(String nomeMapa) {
		this.nomeMapa = nomeMapa;
	}

	public List<CaminhoMalha> getCaminhos() {
		return caminhos;
	}

	public void setCaminhos(List<CaminhoMalha> caminhos) {
		this.caminhos = caminhos;
	}

	/**
	 * Considera no equals apenas o nome do mapa
	 */
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		MalhaViaria malhaViaria = (MalhaViaria) o;

		return this.nomeMapa.equals(malhaViaria.nomeMapa);
	}

	
	
	/**
	 * Considera no hash apenas o nome do mapa
	 */
	@Override
	public int hashCode() {
		
		return new HashCodeBuilder(89687,6545).append(this.nomeMapa).hashCode();
		
	}
	

	
	@Override
	public String toString() {
		
		return "Malha viaria:"+this.nomeMapa;
	}
}
