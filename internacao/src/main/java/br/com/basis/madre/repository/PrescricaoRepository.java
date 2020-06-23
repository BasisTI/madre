package br.com.basis.madre.repository;
import br.com.basis.madre.domain.Prescricao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prescricao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescricaoRepository extends JpaRepository<Prescricao, Long> {

}
