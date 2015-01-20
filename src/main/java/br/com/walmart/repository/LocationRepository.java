package br.com.walmart.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import br.com.walmart.vo.Location;

/**
 * Repositório basico de Location. Contém as operações básicas (CRUD) para Location.
 * @author darcio
 *
 */
@Component
@RepositoryRestResource(collectionResourceRel = "location", path = "location")
public interface LocationRepository extends GraphRepository<Location> {

	/**
	 * Busca uma locação por nome da locação e mapa
	 * @param nameStartLocation
	 * @param nomeMapa
	 * @return
	 */
	Location findByNameAndMapa(String nameStartLocation, String nomeMapa);
	
} 