package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.SituacaoRequisicao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SituacaoRequisicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SituacaoRequisicaoRepository extends JpaRepository<SituacaoRequisicao, Long> {
}
