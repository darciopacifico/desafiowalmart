package walmart;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@Component
@RepositoryRestResource(collectionResourceRel = "location", path = "location")
public interface LocationRepository extends GraphRepository<Location> {

	

	Location findByNameAndMapa(String nameStartLocation, String nomeMapa);

	
	
	
	
} 