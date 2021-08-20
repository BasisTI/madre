package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Instituicao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Instituicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
}
