package walmart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.CostEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.RelationshipExpander;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.Traversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.util.CollectionUtils;

@Configuration
@EnableNeo4jRepositories(basePackages = "walmart")
public class DesafioWalmartApplication extends Neo4jConfiguration implements CommandLineRunner {

	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	public DesafioWalmartApplication() {
		setBasePackage("walmart");
	}

	@Bean
	public GraphDatabaseService graphDatabaseService() {

		GraphDatabaseService gds = new SpringRestGraphDatabase("http://localhost:7474/db/data");

		return gds;
		// return new GraphDatabaseFactory().newEmbeddedDatabase("desafiowalmart.db");
	}

	/**
	 * @Autowired Neo4jTemplate template;
	 */
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	GraphDatabase graphDatabase;

	
	
  
	public void run(String... args) throws Exception {

		
		//zeraBanco();

		Location locationA = new Location("A");
		Location locationB = new Location("B");
		Location locationC = new Location("C");
		Location locationD = new Location("D");
		Location locationE = new Location("E");

		Transaction tx = graphDatabase.beginTx();
		try {
			locationRepository.save(locationA);
			locationRepository.save(locationB);
			locationRepository.save(locationC);
			locationRepository.save(locationD);
			locationRepository.save(locationE);
			/*
	*/
			/**
			 * A B 10 
			 * B D 15 
			 * A C 20 
			 * C D 30 
			 * B E 50 
			 * D E 30
			 */

			
			/*
			locationA = locationRepository.findByName(locationA.getName());
			locationA.connectTo(locationB, 10f);
			locationA.connectTo(locationC, 20f);
			locationRepository.save(locationA);

			locationB = locationRepository.findByName(locationB.getName());
			locationB.connectTo(locationD, 15f);
			locationB.connectTo(locationE, 50f);
			locationRepository.save(locationB);

			locationC = locationRepository.findByName(locationC.getName());
			locationC.connectTo(locationD, 30f);
			locationRepository.save(locationC);

			locationD = locationRepository.findByName(locationD.getName());
			locationD.connectTo(locationE, 30f);
			locationRepository.save(locationD);
			*/
			
			

			locationA.connectTo(locationB, 10f);
			locationA.connectTo(locationC, 20f);
			locationB.connectTo(locationD, 15f);
			locationB.connectTo(locationE, 50f);
			locationC.connectTo(locationD, 30f);
			locationD.connectTo(locationE, 30f);
			
			List col = CollectionUtils.arrayToList(new Location[]{
					locationA,
					locationB,
					locationC,
					locationD,
					locationE
			});			
			
			
			
			locationRepository.save(col);
			/*
			*/

			/*
			 * locationRepository.save(r1); locationRepository.save(r2); locationRepository.save(r3); locationRepository.save(r4); locationRepository.save(r5); locationRepository.save(r6);
			 */


			locationA = locationRepository.findByName(locationA.getName());

			System.out.println("Lookup each person by name...");
			for (String name : new String[] { 
					locationA.getName(), 
					locationB.getName(), 
					locationC.getName(), 
					locationD.getName(), 
					locationE.getName() 
					}) {
				Location localtion = locationRepository.findByName(name);

				System.out.println(localtion.getName());

				Set<Route> conns = localtion.connections;

				for (Route rout : conns) {

					System.out.println(" liga-se aa cidade " + rout.getEndLocation().getName()+ " distancia "+rout.getDistance()+"Km");
					// System.out.println(" liga-se aa cidade " + route.getEndLocation().getName() +" atraves de " + route.getConnectionType() +". Distancia "+route.getDistance() );

				}

			}
			/*
			*/

			tx.success();
		} finally {
			tx.close();
		}

	}

	private void zeraBanco() {

		Transaction tx = graphDatabase.beginTx();

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			graphDatabase.queryEngine().query("match (n) delete n", map);
			graphDatabase.queryEngine().query("MATCH (n)-[r]-() DELETE n, r", map);
			graphDatabase.queryEngine().query("match (n) delete n", map);
			graphDatabase.queryEngine().query("MATCH (n)-[r]-() DELETE n, r", map);
			tx.success();
		} catch (Exception e) {
			tx.close();
			// TODO: handle exception
		}

	}

	public static void main(String[] args) throws Exception {

		// FileUtils.deleteRecursively(new File("desafiowalmart.db"));
		SpringApplication.run(DesafioWalmartApplication.class, args);
	}
}
