package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoDiagnostico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPrescricaoDiagnostico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPrescricaoDiagnosticoRepository extends JpaRepository<ItemPrescricaoDiagnostico, Long> {

}
