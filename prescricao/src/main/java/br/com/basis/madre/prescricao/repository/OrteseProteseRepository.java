package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.OrteseProtese;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrteseProtese entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrteseProteseRepository extends JpaRepository<OrteseProtese, Long> {

}
