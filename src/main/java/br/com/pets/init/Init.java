package br.com.pets.init;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.pets.entity.Cliente;
import br.com.pets.entity.Pet;
import br.com.pets.entity.User;
import br.com.pets.entity.UserRole;
import br.com.pets.repository.RegistroGeralRepository;
import br.com.pets.service.ClienteService;
import br.com.pets.service.PetService;
import br.com.pets.service.UserService;

@SuppressWarnings("ALL")
@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	RegistroGeralRepository registroGeralRepository;

	private final UserService userService;
	private final ClienteService clienteService;
	private final PetService petService;

	public Init(UserService userService,ClienteService clienteService, PetService petService) {
		this.userService = userService;
		this.clienteService = clienteService;
		this.petService = petService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		LocalDateTime localDateTime = LocalDateTime.now();
		int dia = 366;

		User root = new User("Administrador", "000.111.222-33", "root@email.com", "root", UserRole.ADMIN.getRole(),
				localDateTime.toLocalDate().minusDays(dia), true);
		userService.save(root);

		User user = new User("User", "111.555.333-22", "user@email.com", "user", UserRole.USER.getRole(),
				localDateTime.toLocalDate().minusDays(dia), true);
		userService.save(user);
		
		Cliente cliente1 = new Cliente();
		cliente1.setCpf("99999999999");
		cliente1.setNome("cliente 1");
		cliente1.setEndereco("Rua teste 1");
		clienteService.save(cliente1);
		
		Cliente cliente2 = new Cliente();
		cliente2.setCpf("88888888888");
		cliente2.setNome("cliente 2");
		cliente1.setEndereco("Rua teste 2");		
		clienteService.save(cliente2);
		
		Cliente cliente3 = new Cliente();
		cliente3.setCpf("77777777777");
		cliente3.setNome("cliente 3");
		cliente1.setEndereco("Rua teste 3");
		clienteService.save(cliente3);
		
		Pet pet =new Pet();
		pet.setCliente(cliente1);
		pet.setIdade("10 anos");
		pet.setNome("Animal 1");
		pet.setPeso("10 kg");
		pet.setRaca("Cachoro");
		petService.save(pet);
		

	}
}
