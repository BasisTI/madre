package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Vinculo;

import br.com.basis.madre.seguranca.service.projection.PessoaResumo;
import br.com.basis.madre.seguranca.service.projection.VinculoResumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vinculo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VinculoRepository extends JpaRepository<Vinculo, Long> {
    Page<VinculoResumo> findAllProjectedVinculoResumoByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
}
