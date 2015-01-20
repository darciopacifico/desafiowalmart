PROPOSTA DE SOLUCAO E JUSTIFICATIVAS:
======================================
Como a solução demandará o armazenamento e processamento de dados em formato de grafo em larga escala, após breve pesquisa, identifiquei o Neo4j como uma boa 
alternativa de banco de dados para grados, sendo ele então a base da arquitetura proposta nesta aplicação. 

O Neo4J possui implementações de buscas de shortestpath utilizando algoritmo de Dijkstra ou AStar. Para a busca de caminho mais econômico, optei 
pelo algoritmo de Djikstra, já que não consegui prever nenhuma heurística aplicavel ao padrão A*. De qualquer maneira a alteração do algoritmo é bastante simples.

O Neo4J possui uma arquitetura com excelente capacidade de escala e administração, inclusive com hot backups, cache etc. O próprio br.com.walmart é case de sucesso do Neo4J.
Portanto também acredito que seja a opção mais escalável e de mais fácil administração.

Para criar a camada de serviços para manutenção das malhas viárias e consulta de caminhos, foi utilizado Spring Data Neo4J e Spring Web MVC, expondo as funcionalidades
como serviços REST.

O Servidor de aplicação utilizado é uma versão embedded do Tomcat, seguindo o padrão do Spring, mas a aplicação pode ser portada para qualquer 
app server padrão JEE sem muitas dificuldades. Da mesma maneira, foi utilizada a versão embedded do Neo4J, para facilitar testes e desenvolvimento. 
É possível portar a aplicacao para utilização de um servidor Neo4j sem maiores dificultdades.

Para construção dos testes unitários optei pelo TesgNG, devido à capacidade deste de encadear a ordem dos métodos de teste.

A análise estática do código foi realizada com o Sonar, utilizando as regras padrão do mesmo. A cobertura de testes foi analisada como plugin maven "cobertura".


PARA EXECUTAR A APLICAÇÃO:
==============================

Com Maven:
	- Basta executar mvn install, para compilação e execuçao dos testes. Os componentes de negócio e controllers estão com >90% de cobertura.
	
	- Os servidores Tomcat e Neo4J server foram configurados em modo embedded. Não é necessário nenhuma preparação destes ambientes.

Com cliente REST:
	- Executar a classe "br.com.walmart.Application", como uma  Java Application padrão.Os servidores Tomcat e Neo4J
	server foram configurados em modo embedded. Não é necessário nenhuma preparação destes ambientes.
	
	- Os scripts de teste estão disponíveis em formato postman, no arquivo "postman_dump.txt"