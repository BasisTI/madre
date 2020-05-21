package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.TipoMedicamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoMedicamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoMedicamentoRepository extends JpaRepository<TipoMedicamento, Long> {

}
