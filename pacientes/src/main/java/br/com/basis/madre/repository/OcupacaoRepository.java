package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Ocupacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Ocupacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OcupacaoRepository extends JpaRepository<Ocupacao, Long> {
}
