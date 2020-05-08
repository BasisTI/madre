package br.com.basis.madre.farmacia.repository;
import br.com.basis.madre.farmacia.domain.Medicamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Medicamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

}
