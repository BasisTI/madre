package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.Recomendacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Recomendacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Long> {
}
