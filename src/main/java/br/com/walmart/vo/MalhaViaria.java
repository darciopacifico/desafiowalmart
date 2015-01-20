package br.com.walmart.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

}
