package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoDieta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPrescricaoDieta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPrescricaoDietaRepository extends JpaRepository<ItemPrescricaoDieta, Long> {

}
