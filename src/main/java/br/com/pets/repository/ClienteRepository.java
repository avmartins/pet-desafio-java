package  br.com.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.pets.entity.Cliente;

/**
 * @author Anderson Virginio Martins
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c from Cliente c where c.nome like %?1% or c.cpf = ?2")
    Iterable<Cliente> findByNameOrCpf(String nome,String cpf);
    
    @Query("select c from Cliente c where UPPER(c.nome) like %?1%")
    Iterable<Cliente> findByName(String nome);
    
    @Query("select c from Cliente c where c.cpf = ?1")
    Iterable<Cliente> findByCpf(String cpf);
    
}
