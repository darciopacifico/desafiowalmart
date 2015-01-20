package walmart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * bean representando uma malha viaria
 *
 * @author darcio
 */
@JsonSerialize
public class MalhaViaria implements Serializable{

	private static final long serialVersionUID = -3829389330918058065L;

	private String nomeMapa;
	
	private List<CaminhoMalha> caminhos=new ArrayList<CaminhoMalha>(20);

	
	
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
