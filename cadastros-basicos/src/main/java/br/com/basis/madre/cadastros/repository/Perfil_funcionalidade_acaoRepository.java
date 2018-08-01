package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.Perfil_funcionalidade_acao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Perfil_funcionalidade_acao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Perfil_funcionalidade_acaoRepository extends JpaRepository<Perfil_funcionalidade_acao, Long> {

}
