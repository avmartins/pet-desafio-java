package br.com.pets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pets.entity.Pet;
import br.com.pets.repository.PetRepository;
import br.com.pets.service.PetService;

@Service
public class PetServiceImpl implements PetService {

	private final PetRepository PetRepository;

	@Autowired
	public PetServiceImpl(PetRepository PetRepository) {
		this.PetRepository = PetRepository;
	}

	@Override
	public Iterable<Pet> findAll() {
		return PetRepository.findAll();
	}

	@Override
	public Pet save(Pet pet) {
		return PetRepository.save(pet);
	}

	@Override
	public void update(Pet pet) {
		PetRepository.save(pet);
	}

	@Override
	public void updateForm(Pet pet) {
		Pet c = PetRepository.findById(pet.getId()).orElse(null);
		
		c.setNome(pet.getNome());
		c.setIdade(pet.getIdade());
		c.setRaca(pet.getRaca());
		c.setPeso(pet.getPeso());
		
		PetRepository.save(c);
	}

	@Override
	public void delete(Pet pet) {
		Pet e = PetRepository.findById(pet.getId()).orElse(null);

		PetRepository.delete(e);
	}

	@Override
	public Pet findById(Long id) {
		return PetRepository.findById(id).orElse(null);
	}
	
	@Override
	public Iterable<Pet> findByName(String nome) {
		return PetRepository.findByName(nome);
	}
	
	@Override
	public Iterable<Pet> findByCliente(Long id) {
		return PetRepository.findByCliente(id);
	}

}
