package walmart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.util.CollectionUtils;

@Configuration
@EnableAutoConfiguration
@EnableNeo4jRepositories(basePackages = "walmart")
public class DesafioWalmartApplication extends Neo4jConfiguration implements CommandLineRunner {

	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	public DesafioWalmartApplication() {
		setBasePackage("walmart");
	}

	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
	    factory.setPort(8081);
	    factory.setSessionTimeout(5, TimeUnit.MINUTES);
	    //factory.addErrorPages(new ErrorPage(HttpStatus.404, "/notfound.html");

	    return factory;
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
	GraphDatabase graphDatabase;

  
	public void run(String... args) throws Exception {

		
		//zeraBanco();

		//carregaBanco();

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
		SpringApplication.run(Application.class, args);
	}
}
