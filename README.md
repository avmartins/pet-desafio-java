# Cadastro de Clientes

Foi crido uma API REST que será usada para o controle do registro de Cliente.

Foi Criado um sistema web que comporta toda a funcionalidade pedida para o controle do registro da Cliente, Usúarios e autenticação.

## REST API

 * CadastroClientesRestController
 
  * Cadastro de Cliente    		- url : http://localhost:8080/Integracao/Cliente/
  * Edição de Cliente   		- url : http://localhost:8080/Integracao/Cliente/{id}
  * Exclusão de Cliente     	- url : http://localhost:8080/Integracao/Cliente/{id}
  * Visualização de Clientes	- url : http://localhost:8080/Integracao/Cliente/buscaTodos
  
  * Cadastro de Pet    			- url : http://localhost:8080/Integracao/Pet/
  * Edição de Pet   			- url : http://localhost:8080/Integracao/Pet/{id}
  * Exclusão de Pet     		- url : http://localhost:8080/Integracao/Pet/{id}
  * Visualização de Pet   		- url : http://localhost:8080/Integracao/Pet/buscaTodos
 
## Arquitetura do Sistema
 
 *** Para a configuração da aplicação foi utilizado o Apache Maven, para isso foram adicionadas dependências do mesmo:

  * Spring Boot 2.3.5.RELEASE;
  * Spring MVC - que adiciona as dependências do Spring MVC;
  * Spring Security - para o desenvolvimento da segurança e login;
  * thymeleaf - para o desenvolvimento da visão;
  * data-jpa - que adiciona as dependências do Spring Data;
  * log4j - que adiciona as dependências do log;
  * test - que adiciona as dependências do test;
  * com.h2database, que é o banco de dados que será utilizado nessa aplicação.
  
##Executar o Sistema Linha de comando

  ### Passo 1

  #### Abra CMD ou Power Shell
  
  #### Ir para o diretorio do projeto
  
  #### Execute : mvn spring-boot:run ( Sobe a aplicação na porta 8080 : http://localhost:8080/  )
 
  #### Abrir url http://localhost:8080/ e navegar e testar usando o arquivo de Evidencias.doc
  
  #### Abrir url http://localhost:8080/h2 se quiser ver o banco ( jdbc:h2:mem:testedb )
  
  ### Passo 2

  #### Abra um segundo CMD ou Power Shell
	
  #### Ir para o diretorio do projeto
  
  #### Execute : mvn test 
  
  ##### Vai executar a Classe de Test ClientesRestControllerTest que testa a API ClientesRestController
  ##### Vai executar a Classe de Test PetsRestControllerTest que testa a API PetsRestController
  
## Executar o Sistema IDE

  ### Abra idea da sua escolha - Foi utilizada a IDE Spring Tools 4
  
  ### Importa o projeto
  
  ### Executa Spring boot App
  
  #### Abrir url http://localhost:8080/ e navegar e testar usando o arquivo de Evidencias.doc
  
  #### Abrir url http://localhost:8080/h2 se quiser ver o banco ( jdbc:h2:mem:testedb )
  
  ### Executa a Classe de Test ClientesRestControllerTest que testa a API ClientesRestController
  ##### Vai executar a Classe de Test PetsRestControllerTest que testa a API PetsRestController
  