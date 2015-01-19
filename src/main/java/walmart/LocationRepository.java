package walmart;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@Component
@RepositoryRestResource(collectionResourceRel = "location", path = "location")
public interface LocationRepository extends PagingAndSortingRepository<Location, Long> {

	Location findByName(String name);
	Collection<Location> findAllByName(String name);

} 