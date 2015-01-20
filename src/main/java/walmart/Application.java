package walmart;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration
@ComponentScan(basePackages="walmart")
@EnableAutoConfiguration
@EnableNeo4jRepositories(basePackages="walmart")
public class Application extends Neo4jConfiguration {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	
	
	
	
	
	@Bean
	public GraphDatabaseService graphDatabaseService(){
		
		//Server neo4j
		//GraphDatabaseService gds = new SpringRestGraphDatabase("http://localhost:7474/db/data");
		GraphDatabaseService gds = new GraphDatabaseFactory().newEmbeddedDatabase("desafioWalmart.db");
		
		return gds;
	}

	
	@Bean
    public ServletRegistrationBean dispatcherRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet());
        registration.setLoadOnStartup(1);    
        registration.addUrlMappings("/");
        return registration;
    }

	
    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet()
    {
        return new DispatcherServlet();
    }
	
    
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
	    factory.setPort(8081);
	    return factory;
	}

}
