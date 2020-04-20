package br.com.basis.madre.repository;

import br.com.basis.madre.domain.SolicitacaoDeInternacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SolicitacaoDeInternacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitacaoDeInternacaoRepository extends JpaRepository<SolicitacaoDeInternacao, Long> {

}
