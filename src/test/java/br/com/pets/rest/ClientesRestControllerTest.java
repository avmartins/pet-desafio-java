package br.com.pets.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.pets.Application;
import br.com.pets.entity.Cliente;

/**
 * @author Anderson Virginio Martins
 * 
 * Claase responsável por testar a IntegracaoRestController 
 *
 */
@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles(profiles = "test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientesRestControllerTest {
	
	public static final String REST_SERVICE_URL = "http://localhost:8080/Integracao/Cliente/";
	
	@Before(value = "")
	public void setUpCliente() {
	    System.out.println("Init");
	}
	
	@After(value = "")
	public void setDownCliente() {
	    System.out.println("End");
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testCadastro() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String plainCredentials="root@email.com:root";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + plainCredentials);

		Cliente cliente = new Cliente();
		cliente.setCpf("12345678901");
		cliente.setNome("cliente 5 teste");
		cliente.setEndereco("M");
		
		HttpEntity<Cliente> request = new HttpEntity<>(cliente, headers);

		final String baseUrl = "http://localhost:8080/Integracao/Cliente";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.postForEntity(uri, request, Cliente.class);
			assertNotNull(request.getBody());
			System.out.println("####################### testCadastro #######################");
			System.out.println("cliente Criado com sucesso!");
		} catch (HttpClientErrorException ex) {
			assertEquals(400, ex.getRawStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Erro não existe request ou header"));
		} catch (Exception ex) {
			assertEquals(500, ex.getMessage());
		}
	}
	
	@Test
	public void testEdicao() {

		RestTemplate restTemplate = new RestTemplate();

		String plainCredentials="root@email.com:root";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + plainCredentials);
				
		Cliente cliente = new Cliente();
		cliente.setId(2L);
		cliente.setCpf("07225612700");
		cliente.setNome("cliente 1-2");
		cliente.setEndereco("Endereço 2");
		
		HttpEntity<Cliente> request = new HttpEntity<>(cliente, headers);

		final String baseUrl = "http://localhost:8080/Integracao/Cliente/2";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.exchange(uri, HttpMethod.PUT, request,Cliente.class);
			assertNotNull(request.getBody());
			System.out.println("####################### testAlteracao #######################");
			System.out.println("Atualização cliente com sucesso!");
		} catch (HttpClientErrorException ex) {
			assertEquals(400, ex.getRawStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Erro não existe request ou header"));
		} catch (Exception ex) {
			assertEquals(500, ex.getMessage());
		}
	}
	
	@Test
	public void testExclusao() {

		RestTemplate restTemplate = new RestTemplate();

		String plainCredentials="root@email.com:root";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + plainCredentials);
				
		Cliente cliente = new Cliente();
		cliente.setId(2L);
				
		HttpEntity<Cliente> request = new HttpEntity<>(cliente, headers);

		final String baseUrl = "http://localhost:8080/Integracao/Cliente/2";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.exchange(uri, HttpMethod.DELETE, request,Cliente.class);
			assertNotNull(request.getBody());
			System.out.println("####################### testRemocao #######################");
			System.out.println("cliente deletado com sucesso!\n");
		} catch (HttpClientErrorException ex) {
			assertEquals(400, ex.getRawStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Erro não existe request ou header"));
		} catch (Exception ex) {
			assertEquals(500, ex.getMessage());
		}
	}

	@Test
	public void testVisualizacao() {		
		
		System.out.println("####################### testVisualizacao #######################");
		
		String plainCreds = "root@email.com:root";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		
		// create an instance of RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// build the request
		HttpEntity request = new HttpEntity(headers);

		// make an HTTP GET request with headers
		ResponseEntity<List> response = restTemplate.exchange(
				REST_SERVICE_URL+"buscaTodos",
		        HttpMethod.GET,
		        request,
		        List.class
		);

		// check response
		if (response.getStatusCode() == HttpStatus.OK) {
			Iterable<Cliente> listaclientees = response.getBody();

            if(listaclientees != null)
            {
                for (Cliente cliente : listaclientees) {
        			System.out.println("Nome : "+cliente.getNome());
        		}

            }
		} else {
			System.out.println("Clientes não existem!");
		}
        
		assertNotNull(response.getBody());	
	}
	
}
