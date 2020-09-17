package  br.com.pets.service;

import  br.com.pets.entity.Pet;

public interface PetService {

	Iterable<Pet> findAll();
	
	Iterable<Pet> findByName(String nome);
	
	Iterable<Pet> findByCliente(Long id);
	
	Pet findById(Long id);

	Pet save(Pet pet);

	void update(Pet pet);

	void updateForm(Pet pet);
	
	void delete(Pet pet);

}
