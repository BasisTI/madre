package br.com.basis.madre.farmacia.repository;
import br.com.basis.madre.farmacia.domain.MedicamentoCID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicamentoCID entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicamentoCIDRepository extends JpaRepository<MedicamentoCID, Long> {

}
