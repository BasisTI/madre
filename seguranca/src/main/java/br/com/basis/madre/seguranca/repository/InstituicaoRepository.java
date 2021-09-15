package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Instituicao;

import br.com.basis.madre.seguranca.service.projection.InstituicaoResumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Instituicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
    Page<InstituicaoResumo> findAllProjectedInstituicaoResumoByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
}
