package br.com.pets.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.pets.Application;
import br.com.pets.entity.Cliente;
import br.com.pets.entity.Pet;

/**
 * @author Anderson Virginio Martins
 * 
 * Claase responsável por testar a IntegracaoRestController 
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles(profiles = "test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PetsRestControllerTest {
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testCadastro() {
		
		Cliente cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setCpf("99999999999");
		cliente1.setNome("cliente 1");
		cliente1.setEndereco("Rua teste 1");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		Pet pet = new Pet();
		pet.setCliente(cliente1);
		pet.setIdade("10 anos");
		pet.setNome("Azulzinho");
		pet.setPeso("10 gramas");
		pet.setRaca("Calopsita");
		
		HttpEntity<Pet> request = new HttpEntity<>(pet, headers);

		final String baseUrl = "http://localhost:8080/Integracao/Pet/";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.postForEntity(uri, request, Pet.class);
			assertNotNull(request.getBody());
			System.out.println("####################### testCadastro #######################");
			System.out.println("pet Criado com sucesso!");
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

		HttpHeaders headers = new HttpHeaders();
		
		Cliente cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setCpf("99999999999");
		cliente1.setNome("cliente 1");
		cliente1.setEndereco("Rua teste 1");
				
		Pet pet = new Pet();
		pet.setId(1L);
		pet.setCliente(cliente1);
		pet.setIdade("10 anos");
		pet.setNome("Azulzinho");
		pet.setPeso("10 gramas");
		pet.setRaca("Calopsita");
		
		HttpEntity<Pet> request = new HttpEntity<>(pet, headers);

		final String baseUrl = "http://localhost:8080/Integracao/Pet/1";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.exchange(uri, HttpMethod.PUT, request,Pet.class);
			assertNotNull(request.getBody());
			System.out.println("####################### testEdicao #######################");
			System.out.println("Atualização pet com sucesso!");
		} catch (HttpClientErrorException ex) {
			assertEquals(400, ex.getRawStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Erro não existe request ou header"));
		} catch (Exception ex) {
			assertEquals(500, ex.getMessage());
		}
	}
	
	@Test
	public void testVisualizacao() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Iterable<Pet>> response = restTemplate.exchange("http://localhost:8080/Integracao/Pet/buscaTodos",
				HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<Pet>>() {
				});

		Iterable<Pet> listapetes = response.getBody();
		
		System.out.println("####################### testVisualizacao #######################");

		for (Pet pet : listapetes) {
			System.out.println("Nome : "+pet.getNome());
		}

		assertNotNull(response.getBody());
	}
	
	
	@Test
	public void testExclusao() {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
				
		Pet pet = new Pet();
		pet.setId(1L);
				
		HttpEntity<Pet> request = new HttpEntity<>(pet, headers);

		final String baseUrl = "http://localhost:8080/Integracao/Pet/1";

		try {
			URI uri = new URI(baseUrl);
			restTemplate.exchange(uri, HttpMethod.DELETE, request,Pet.class);
			assertNotNull(request.getBody());
			System.out.println("####################### testExclusao #######################");
			System.out.println("pet deletado com sucesso!\n");
		} catch (HttpClientErrorException ex) {
			assertEquals(400, ex.getRawStatusCode());
			assertEquals(true, ex.getResponseBodyAsString().contains("Erro não existe request ou header"));
		} catch (Exception ex) {
			assertEquals(500, ex.getMessage());
		}
	}

}
