package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.FuncionalidadeAcao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the FuncionalidadeAcao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Funcionalidade_acaoRepository extends JpaRepository<FuncionalidadeAcao, Long> {

	@Query("SELECT id FROM FuncionalidadeAcao WHERE id_acao = :#{#acao} AND id_funcionalidade = :#{#func}")
	Integer pegarIds(@Param("acao") Integer acao, @Param("func") Integer func);
}
