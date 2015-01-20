package walmart;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowire;
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
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Classe base contendo a configuração do contexto da aplicação, com um EmbeddedServletContainer
 * 
 * @author darcio
 */
@Configuration
@ComponentScan(basePackages = "walmart")
@EnableAutoConfiguration
@EnableNeo4jRepositories(basePackages = "walmart")
public class Application extends Neo4jConfiguration {

	/**
	 * Construtor padrao. Apenas seta o pacote base dos repositórios.
	 */
	public Application() {
		setBasePackage("walmart");
	}
	
	
	/**
	 * Inicia a aplicação como Java Application, Tomcat Em
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Cria a o bean que será o ponto de acesso ao Neo4J. Neste ponto pode-se utilizar um servidor neo4j embedded
	 * 
	 * @return
	 */
	@Bean
	public GraphDatabaseService graphDatabaseService() {

		// Server neo4j
		GraphDatabaseService gds = new SpringRestGraphDatabase("http://localhost:7474/db/data");
		
		//TODO: Servidor embedded seria melhor para testes, mas seria necessário configurar um gerenciador de transações. Farei isso, se der tempo
		// GraphDatabaseService gds = new GraphDatabaseFactory().newEmbeddedDatabase("desafioWalmart.db");

		return gds;
	}

	
	/**
	 * Cria o bean registrador de servlets
	 * @return
	 */
	@Bean
	public ServletRegistrationBean dispatcherRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet());
		registration.setLoadOnStartup(1);
		registration.addUrlMappings("/");
		return registration;
	}

	
	/**
	 * Bean do servlet dispatcher padrao do framework Spring MVC
	 * @return
	 */
	@Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	/**
	 * Bean do container Tomcat Embedded para a aplicação
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.setPort(8081);
		return factory;
	}

}
