package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.TiposDeQualificacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TiposDeQualificacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiposDeQualificacaoRepository extends JpaRepository<TiposDeQualificacao, Long> {
}
