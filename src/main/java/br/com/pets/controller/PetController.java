package br.com.pets.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.pets.entity.Pet;
import br.com.pets.exception.PetNotFoundException;
import br.com.pets.service.ClienteService;
import br.com.pets.service.PetService;

@RestController
@RequestMapping("/pets")
public class PetController {

	private static final Logger logger = Logger.getLogger(PetController.class);

	private final PetService petService;
	private final ClienteService clienteService;
	
	public PetController(PetService petService,ClienteService clienteService) {
		this.clienteService = clienteService;
		this.petService = petService;
	}

	@GetMapping("list")
	public ModelAndView list(ModelMap modelMap) {
		modelMap.addAttribute("pets", petService.findAll());
		modelMap.addAttribute("pet", new Pet());
		modelMap.addAttribute("clientes", clienteService.findAll());
		return new ModelAndView("/fragments/pets/list", modelMap);
	}

	@GetMapping("/new")
	public ModelAndView newpet(ModelMap modelMap) {
		modelMap.addAttribute("pet", new Pet());
		modelMap.addAttribute("clientes", clienteService.findAll());
		return new ModelAndView("/fragments/pets/new", modelMap);
	}

	@PostMapping("/save")
	public ModelAndView savepet(@ModelAttribute("pet") Pet pet) {
		try {
			petService.save(pet);
		} catch (Exception e) {
			logger.info("Erro ao incluir a pet");
		}

		return new ModelAndView("redirect:/pets/list");
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {

		Pet pet = petService.findById(id);

		if (pet == null)
			throw new PetNotFoundException("id-" + id);
		
		model.addAttribute("pet", pet);
		model.addAttribute("cliente", pet.getCliente());
		return new ModelAndView("fragments/pets/edit", model);
	}

	@PostMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Long id, Pet pet) {
		petService.updateForm(pet);
		return new ModelAndView("redirect:/pets/list");
	}

	@GetMapping("/remove/{id}")
	public ModelAndView remove(@PathVariable("id") Long id) {
		Pet pet = petService.findById(id);

		petService.delete(pet);
		logger.info("pet excluído");

		return new ModelAndView("redirect:/pets/list");
	}
	
	@PostMapping("/buscar")
	public ModelAndView buscar(@ModelAttribute("pet") Pet pet, ModelMap modelMap) {
		modelMap.addAttribute("pet",  pet);
		
		modelMap.addAttribute("pets", petService.findAll());
		
		modelMap.addAttribute("clientes", clienteService.findAll());
		
		if (pet.getNome() != null && !pet.getNome().equals("")) {
			modelMap.addAttribute("pets", petService.findByName(pet.getNome().toUpperCase()));
		}
		
		if (pet.getCliente() != null ) {
			modelMap.addAttribute("pets", petService.findByCliente(pet.getCliente().getId()));
		}
		
		return new ModelAndView("/fragments/pets/list", modelMap);
	}
	
	 public boolean isValidDate(String date) {
      	  try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate d = LocalDate.parse(date,formatter);	
            return true;
      	  } catch (DateTimeParseException e) {
      	  	return false;
      	  }   
      }

}
