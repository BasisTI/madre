package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.SolicitacaoExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SolicitacaoExame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitacaoExameRepository extends JpaRepository<SolicitacaoExame, Long> {

    Long countAllByIdIsNotNull();
}
