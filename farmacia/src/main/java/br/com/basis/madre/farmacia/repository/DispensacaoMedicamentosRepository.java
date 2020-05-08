package br.com.basis.madre.farmacia.repository;
import br.com.basis.madre.farmacia.domain.DispensacaoMedicamentos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DispensacaoMedicamentos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispensacaoMedicamentosRepository extends JpaRepository<DispensacaoMedicamentos, Long> {

}
