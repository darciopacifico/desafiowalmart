package walmart;

import java.util.Collection;

import org.springframework.data.neo4j.repository.GraphRepository;


public interface LocationRepository extends GraphRepository<Location> {

	Location findByName(String name);
	Collection<Location> findAllByName(String name);

	
	
	
} 