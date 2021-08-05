package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Pessoa;

import br.com.basis.madre.seguranca.service.projection.PessoaResumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Page<PessoaResumo> findAllProjectedPessoaResumoByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
