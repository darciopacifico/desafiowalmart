PROPOSTA DE SOLUCAO E JUSTIFICATIVAS:
======================================
Como a solução demandará o armazenamento e processamento de dados em formato de grafo em larga escala, após breve pesquisa, identifiquei o Neo4j como uma boa 
alternativa de banco de dados para grados, sendo ele então a base da arquitetura proposta nesta aplicação. 

O Neo4J possui implementações de busca shortestpath, utilizando algoritmo de Dijkstra ou AStar. Para a busca de caminho mais econômico, optei 
pelo algoritmo de Djikstra, já que não consegui prever nenhuma heurística aplicavel ao padrão A*.

O Neo4J possui uma arquitetura com excelente capacidade de escala e administração, inclusive com hot backups, cache etc. O próprio walmart é case de sucesso do Neo4J.

Para criar a camada de serviços para manutenção das malhas viárias e consulta de caminhos, foi utilizado Spring Data Neo4J e Spring Web MVC, expondo as funcionalidades
como serviços REST.


PARA EXECUTAR A APLICAÇÃO:
==============================
É necessário ter um banco de dados Neo4J rodando na máquina local.
Executar a classe "walmart.Application", como uma  Java Application padrão.
Os scripts de teste estão disponíveis em formato postman, no arquivo "postman_dump.txt"
 

