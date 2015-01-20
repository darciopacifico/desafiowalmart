Proposta de Solução e Justificativas:

Como a solução demandará o armazenamento e processamento de dados em formato de grafo em larga escala, após breve pesquisa, identifiquei o Neo4j como uma boa 
alternativa de banco de dados, sendo ele então a base da arquitetura proposta nesta aplicação. 

O Neo4J possui uma arquitetura com excelente capacidade de escala e administração, inclusive com hot backups, cache etc. O próprio walmart é case de sucesso do Neo4J.

Para criar a camada de serviços para manutenção das malhas viárias e consulta de caminhos, foi utilizado Spring Data Neo4J e Spring Web MVC, expondo as funcionalidades
como serviços REST.


 

