package br.com.walmart;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import br.com.walmart.exception.WalmartRuntimeException;

/**
 * Classe base contendo a configuração do contexto da aplicação, com um EmbeddedServletContainer
 * 
 * @author darcio
 */
@Configuration
@ComponentScan(basePackages = "br.com.walmart")
@EnableAutoConfiguration
@EnableNeo4jRepositories(basePackages = "br.com.walmart")
public class Application extends Neo4jConfiguration {

	private static final String DESAFIO_WALMART_DB = "desafioWalmart.db";
	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	
	
	/**
	 * Construtor padrao. Apenas seta o pacote base dos repositórios.
	 */
	public Application() {
		setBasePackage("br.com.walmart");
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
	@Bean(destroyMethod="shutdown")
	public GraphDatabaseService graphDatabaseService() {

  	if(log.isDebugEnabled()){
  		log.debug("Criando banco de dados Neo4J embedded!");
  	}
  	
		final GraphDatabaseService gds;
		try {
			gds = (GraphDatabaseService) new GraphDatabaseFactory().newEmbeddedDatabase(DESAFIO_WALMART_DB);
		} catch (Exception e) {
			
			log.error("ERRO!! VERIFIQUE SE O ARQUIVO DO BANCO DE DADOS ("+DESAFIO_WALMART_DB+") NAO ESTA EM LOCK!  ");
			log.error("AO ENCERRAR A APLICACAO SEMPRE AGUARDE O SHUTDOWN DO BANCO DE DADOS. ESTA OPERACAO PODE DEMORAR ALGUNS SEGUNDOS!  ");
			log.error("Erro ao tentar instanciar base Neo4J Embedded!  ",e);
			
			throw new WalmartRuntimeException("Erro ao criar base Embedded!", e);
		}
		
	
    // Registers a shutdown hook for the Neo4j instance so that it
    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
    // running application).
    Runtime.getRuntime().addShutdownHook( new Thread()
    {
        @Override
        public void run()
        {
        	
        	if(log.isDebugEnabled()){
        		log.debug("desligando banco de dados embedded Neo4J. Aguarde!!!...");
        	}
        	gds.shutdown();
        }
    } );
	
		
		return gds;
	}

	/**
	 * Cria o bean registrador de servlets
	 * 
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
	 * 
	 * @return
	 */
	@Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	/**
	 * Bean do container Tomcat Embedded para a aplicação
	 * 
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.setPort(8081);
		return factory;
	}

}
