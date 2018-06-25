package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.Funcionalidade;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Funcionalidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long> {

}
