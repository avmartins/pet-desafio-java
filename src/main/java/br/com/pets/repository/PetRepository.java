package  br.com.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.pets.entity.Pet;

/**
 * @author Anderson Virginio Martins
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p where UPPER(p.nome) like %?1%")
    Iterable<Pet> findByName(String nome);
    
    @Query("select p from Pet p where p.cliente.id = ?1")
    Iterable<Pet> findByCliente(Long id);
    
}
