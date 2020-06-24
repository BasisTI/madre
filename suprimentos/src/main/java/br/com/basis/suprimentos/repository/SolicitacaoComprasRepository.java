package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.SolicitacaoCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SolicitacaoCompras entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitacaoComprasRepository extends JpaRepository<SolicitacaoCompras, Long> {

}
