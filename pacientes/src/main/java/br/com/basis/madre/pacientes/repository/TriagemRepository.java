package br.com.basis.madre.pacientes.repository;

import br.com.basis.madre.pacientes.domain.Triagem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Triagem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TriagemRepository extends JpaRepository<Triagem, Long> {

}
