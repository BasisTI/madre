package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.Funcionalidade;
import br.com.basis.madre.cadastros.domain.Funcionalidade_acao;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Funcionalidade_acao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Funcionalidade_acaoRepository extends JpaRepository<Funcionalidade_acao, Long> {
	
//	@Query("SELECT Funcionalidade.id, Acao.id FROM Acao, Funcionalidade")
//	List<Funcionalidade> pegaIds();
}
