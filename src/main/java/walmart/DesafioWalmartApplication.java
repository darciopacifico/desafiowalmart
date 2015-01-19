package walmart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.CostEvaluator;
import org.neo4j.graphalgo.EstimateEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipExpander;
import org.neo4j.graphdb.ResourceIterator;
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
import org.springframework.data.neo4j.conversion.Result;
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
	
	@Autowired
	GraphDatabaseService db;
	


	private void shortestPathDijkstra() {
		
		System.out.println();
		System.out.println("Menor caminho ");
		
		PathExpander<Object> expander = PathExpanders.allTypesAndDirections();
		
		
		//PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(expander, "distance");
		
		
		EstimateEvaluator<Double> estimateEvaluator = new EstimateEvaluator<Double>() {
			@Override
			public Double getCost(Node arg0, Node arg1) {
				
				System.out.println("heur: " + arg0.getProperty("name") +" " +arg1.getProperty("name"));
				
				return 10d;
			}
		};
		
		
		CostEvaluator<Double> lengthEvaluator =  new CostEvaluator<Double>() {
			
			public Double getCost(Relationship relationship, Direction direction) {
				
				//STREET, ROAD, FREEWAY, FERRY, DIRT_ROAD, BRIDGE; 
				
				Double distance = (Double) relationship.getProperty("distance");
				
			 	Node startNode = relationship.getStartNode();
			 	Node endNode = relationship.getEndNode();
				
			 	System.out.println("heur relationship from "+startNode.getProperty("name")+" to " + endNode.getProperty("name") +" "+relationship.getProperty("type"));
				
				String type = (String) relationship.getProperty("type");
				
				EnumConnectionType eType = EnumConnectionType.valueOf(type);
				
				
				
				return distance  ;
			}
		};
		
		
		
		PathFinder<WeightedPath> finder = GraphAlgoFactory.aStar(expander, lengthEvaluator, estimateEvaluator);
		
		

		
		//findExecutionEngine();
		
		
		Result<Map<String, Object>> result = graphDatabase.queryEngine().query("match (n) where n.name='A' or n.name='E'   return n;", null);
		
		Iterator<Map<String, Object>> it = result.iterator();

		
		List<Node> nodes = new ArrayList<Node>();
		
		
		while(it.hasNext()){
			Map<String, Object> obj = (Map<String, Object>) it.next();
			Node node = (Node) obj.get("n");
			nodes.add(node);
		}
		
		
		Node nodeA = nodes.get(0);
		Node nodeB = nodes.get(1);
		
		
		WeightedPath path = finder.findSinglePath(nodeA, nodeB);
		
		
		Iterable<Relationship> iter = path.relationships();
		
		System.out.println("Custo total do caminho "+path.weight());
		
		for (Relationship rel: iter) {
			
			Node startNode = rel.getStartNode(); 
			Node endNode = rel.getEndNode();
			
			Double distance = (Double) rel.getProperty("distance");
			
			System.out.println("saindo de "+startNode.getProperty("name")+" ate "+endNode.getProperty("name")+ " distancia "+distance+"Km");
			
			
		}
	}

	private void findExecutionEngine() {
		ExecutionEngine engine = new ExecutionEngine( db );
		
		ExecutionResult result = engine.execute( "match (n {name:\"A\"}) return n;");
		
		ResourceIterator<Node> col = result.columnAs("n");
		
		while(col.hasNext()){
			
			Node node = col.next();
			
			System.out.println(node);
			
		}
	}
	
  
	public void run(String... args) throws Exception {

		
		zeraBanco();

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
			
			
			/**
			 * A B 10 
			 * B D 15 
			 * A C 20 
			 * C D 30 
			 * B E 50 
			 * D E 30
			 */
			locationA.connectTo(locationB, 10f);
			locationB.connectTo(locationD, 15f, EnumConnectionType.DIRT_ROAD);
			locationA.connectTo(locationC, 20f, EnumConnectionType.FREEWAY);
			locationC.connectTo(locationD, 30f);
			locationB.connectTo(locationE, 50f);
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
		
		
		
		shortestPathDijkstra();

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
