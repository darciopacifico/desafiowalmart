package walmart;

import org.springframework.data.neo4j.repository.GraphRepository;


public interface LocationRepository extends GraphRepository<Location> {

	Location findByName(String name);

} 