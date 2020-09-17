package br.com.pets.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.pets.entity.Cliente;
import br.com.pets.exception.CPFInvalidoException;
import br.com.pets.exception.ClienteNotFoundException;
import br.com.pets.service.ClienteService;
import br.com.pets.service.util.ValidaCPF;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private static final Logger logger = Logger.getLogger(ClienteController.class);

	private final ClienteService clienteService;
	
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@GetMapping("list")
	public ModelAndView list(ModelMap modelMap) {
		modelMap.addAttribute("clientes", clienteService.findAll());
		modelMap.addAttribute("cliente", new Cliente());
		return new ModelAndView("/fragments/clientes/list", modelMap);
	}

	@GetMapping("/new")
	public ModelAndView newcliente(ModelMap modelMap) {
		modelMap.addAttribute("cliente", new Cliente());
		return new ModelAndView("/fragments/clientes/new", modelMap);
	}

	@PostMapping("/save")
	public ModelAndView savecliente(@ModelAttribute("cliente") Cliente cliente) {
		try {
			if(cliente.getCpf() == null || ( cliente.getCpf() != null && !ValidaCPF.isCPF(cliente.getCpf())) )
				throw new CPFInvalidoException("id-" + cliente.getCpf());
			
			clienteService.save(cliente);
		} catch (Exception e) {
			logger.info("Erro ao incluir a cliente");
		}

		return new ModelAndView("redirect:/clientes/list");
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {

		Cliente cliente = clienteService.findById(id);

		if (cliente == null)
			throw new ClienteNotFoundException("id-" + id);
		
		model.addAttribute("cliente", cliente);
		return new ModelAndView("fragments/clientes/edit", model);
	}

	@PostMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Long id, Cliente cliente) {
		clienteService.updateForm(cliente);
		return new ModelAndView("redirect:/clientes/list");
	}

	@GetMapping("/remove/{id}")
	public ModelAndView remove(@PathVariable("id") Long id) {
		Cliente cliente = clienteService.findById(id);

		clienteService.delete(cliente);
		logger.info("cliente excluído");

		return new ModelAndView("redirect:/clientes/list");
	}
	
	@PostMapping("/buscar")
	public ModelAndView buscar(@ModelAttribute("cliente") Cliente cliente, ModelMap modelMap) {
		modelMap.addAttribute("cliente",  cliente);
		
		modelMap.addAttribute("clientes", clienteService.findAll());
		
		if (cliente.getCpf() != null && !cliente.getCpf().equals("")) {
			modelMap.addAttribute("clientes", clienteService.findByCpf(cliente.getCpf()));
		}
		
		if (cliente.getNome() != null && !cliente.getNome().equals("")) {
			modelMap.addAttribute("clientes", clienteService.findByName(cliente.getNome().toUpperCase()));
		}
		
		
		
		return new ModelAndView("/fragments/clientes/list", modelMap);
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
