package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.Acao;
import br.com.basis.madre.cadastros.domain.Funcionalidade;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Acao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcaoRepository extends JpaRepository<Acao, Long> {
//	@Query("SELECT id FROM Acao")
//	List<Integer> pegaIds();
}
