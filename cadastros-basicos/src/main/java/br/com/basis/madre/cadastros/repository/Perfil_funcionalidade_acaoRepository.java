package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.PerfilFuncionalidadeAcao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PerfilFuncionalidadeAcao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Perfil_funcionalidade_acaoRepository extends JpaRepository<PerfilFuncionalidadeAcao, Long> {

}
