package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.PerfilFuncionalidadeAcao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PerfilFuncionalidadeAcao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilFuncionalidadeAcaoRepository extends JpaRepository<PerfilFuncionalidadeAcao, Long> {

}
