package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.AcaoTemp;
import br.com.basis.madre.cadastros.domain.Funcionalidade;
import br.com.basis.madre.cadastros.domain.Funcionalidade_acao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Funcionalidade_acao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Funcionalidade_acaoRepository extends JpaRepository<Funcionalidade_acao, Long> {
	
	@Query("SELECT id FROM Funcionalidade_acao WHERE id_acao = :#{#acao} AND id_funcionalidade = :#{#func}")
	Integer pegarIds(@Param("acao") Integer acao, @Param("func") Integer func);
}
