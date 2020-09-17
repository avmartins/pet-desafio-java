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

import br.com.pets.entity.Pet;
import br.com.pets.exception.PetNotFoundException;
import br.com.pets.service.PetService;

@RestController
@RequestMapping("/Integracao")
@CrossOrigin(origins = { "http://localhost:8080" })
public class PetsRestController {

	private final PetService PetService;

	public PetsRestController(PetService PetService) {
		this.PetService = PetService;
	}

	// Cadastro
	@PostMapping("/Pet/")
	public ResponseEntity<Object> cadastro(@RequestBody Pet Pet) {
		Pet savedPet = PetService.save(Pet);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPet.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	// Edição
	@PutMapping("/Pet/{id}")
	public ResponseEntity<Object> alteracao(@RequestBody Pet Pet, @PathVariable long id) {

		Pet PetOptional = PetService.findById(id);

		if (PetOptional == null)
			return ResponseEntity.notFound().build();

		Pet.setId(id);

		PetService.save(Pet);

		return ResponseEntity.noContent().build();
	}

	// Exclusão
	@DeleteMapping("/Pet/{id}")
	public void remocao(@PathVariable long id) {
		Pet Pet = PetService.findById(id);
		PetService.delete(Pet);
	}

	// Visualização
	@GetMapping("/Pet/buscaTodos")
	public Iterable<Pet> consultaAll() {
		return PetService.findAll();
	}
		
	@GetMapping("/Pet/{id}")
	public Pet consultaId(@PathVariable long id) throws PetNotFoundException {

		Pet Pet = PetService.findById(id);

		return Pet;
	}

}
