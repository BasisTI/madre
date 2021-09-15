package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.TiposDeQualificacao;

import br.com.basis.madre.seguranca.service.projection.TiposDeQualificacaoResumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TiposDeQualificacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiposDeQualificacaoRepository extends JpaRepository<TiposDeQualificacao, Long> {
    Page<TiposDeQualificacaoResumo> findAllProjectedTiposDeQualificacaoResumoByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
}
