package br.com.basis.madre.exames.repository;

import br.com.basis.madre.exames.domain.Recomendacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Recomendacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Long> {
}
