package walmart;


import java.io.File;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;

@Configuration
@EnableNeo4jRepositories(basePackages = "walmart")
public class DesafioWalmartApplication  extends Neo4jConfiguration implements CommandLineRunner {

	
	
    public DesafioWalmartApplication() {
        setBasePackage("walmart");
    }

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase("desafiowalmart.db");
    }

    /**
    @Autowired Neo4jTemplate template;
     * 
     */
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    GraphDatabase graphDatabase;

    
    public void run(String... args) throws Exception {
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
			A B 10
			B D 15
			A C 20
			C D 30
			B E 50
			D E 30             
             */
            
            
            //penso que posso ignorar este passo, OK?
            locationA = locationRepository.findByName(locationA.getName());
            locationB = locationRepository.findByName(locationB.getName());
            locationC = locationRepository.findByName(locationC.getName());
            locationD = locationRepository.findByName(locationD.getName());
            locationE = locationRepository.findByName(locationE.getName());
            
            
            Route r1 = locationA.connectTo(locationB, 10f, "ROAD");
            Route r2 = locationB.connectTo(locationD, 15f, "ROAD");
            Route r3 = locationA.connectTo(locationC, 20f, "ROAD");
            Route r4 = locationC.connectTo(locationD, 30f, "ROAD");
            Route r5 = locationB.connectTo(locationE, 50f, "ROAD");
            Route r6 = locationD.connectTo(locationE, 30f, "ROAD");

            /*
            locationRepository.save(r1);
            locationRepository.save(r2);
            locationRepository.save(r3);
            locationRepository.save(r4);
            locationRepository.save(r5);
            locationRepository.save(r6);
            */
            
            
            locationRepository.save(locationA);
            locationRepository.save(locationB);
            locationRepository.save(locationC);
            locationRepository.save(locationD);
            locationRepository.save(locationE);

            
            locationA = locationRepository.findByName(locationA.getName());

            System.out.println("Lookup each person by name...");
            for (String name: new String[]{locationA.getName(), locationB.getName(), locationC.getName()}) {
                Location localtion = locationRepository.findByName(name);
                
                
				System.out.println(localtion.getName());
				
				Set<Route> conns = localtion.getConnections();
				
				for (Route route : conns) {
					
					System.out.println(" liga-se aa cidade " + route.getEndLocation().getName() +" atraves de " + route.getConnectionType() +". Distancia "+route.getDistance()  );
					
				}
				
            }
            /*
			*/

            tx.success();
        } finally {
            tx.close();
        }

    }

    
    public static void main(String[] args) throws Exception {
    	
        FileUtils.deleteRecursively(new File("desafiowalmart.db"));
    	SpringApplication.run(DesafioWalmartApplication.class, args);
	}
}
