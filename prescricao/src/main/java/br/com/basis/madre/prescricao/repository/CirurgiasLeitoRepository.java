package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.CirurgiasLeito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CirurgiasLeito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CirurgiasLeitoRepository extends JpaRepository<CirurgiasLeito, Long> {

}
