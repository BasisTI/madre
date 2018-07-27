package br.com.basis.madre.cadastros.repository;

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
//	
//	@Query("SELECT id_funcionalidade, id_acao FROM Funcionalidade_acao WHERE id_funcionalidade = %:func% AND id_acao = %:acao%")
//	Optional<Funcionalidade_acao> selectIds(@Param("func") Integer func, @Param("acao") Integer acao);
//	
//	Optional<Funcionalidade_acao> findById_funcionalidadeAndId_acao(Long Id_funcionalidade, Long Id_acao);
}
