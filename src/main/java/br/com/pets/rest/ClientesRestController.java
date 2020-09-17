package br.com.pets.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.pets.entity.Cliente;
import br.com.pets.exception.ClienteNotFoundException;
import br.com.pets.service.ClienteService;

@RestController
@RequestMapping("/Integracao")
@CrossOrigin(origins = { "http://localhost:8080" })
public class ClientesRestController {

	private final ClienteService ClienteService;

	public ClientesRestController(ClienteService ClienteService) {
		this.ClienteService = ClienteService;
	}

	// Cadastra Cliente
	@PostMapping("/Cliente/")
	public ResponseEntity<Object> cadastro(@RequestBody Cliente Cliente) {
		Cliente savedCliente = ClienteService.save(Cliente);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCliente.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	// Altera Cliente
	@PutMapping("/Cliente/{id}")
	public ResponseEntity<Object> alteracao(@RequestBody Cliente Cliente, @PathVariable long id) {

		Cliente ClienteOptional = ClienteService.findById(id);

		if (ClienteOptional == null)
			return ResponseEntity.notFound().build();

		Cliente.setId(id);

		ClienteService.save(Cliente);

		return ResponseEntity.noContent().build();
	}

	// Remove Cliente
	@DeleteMapping("/Cliente/{id}")
	public void remocao(@PathVariable long id) {
		Cliente Cliente = ClienteService.findById(id);
		ClienteService.delete(Cliente);
	}

	// Consulta todas os Clientes
	@GetMapping("/Cliente/buscaTodos")
	public Iterable<Cliente> consultaAll() {
		return ClienteService.findAll();
	}
	
	@GetMapping("/Cliente/{id}")
	public Cliente consultaId(@PathVariable long id) throws ClienteNotFoundException {

		Cliente Cliente = ClienteService.findById(id);

		return Cliente;
	}
	

}
