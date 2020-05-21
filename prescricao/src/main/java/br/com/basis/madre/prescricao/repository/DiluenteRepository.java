package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.Diluente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Diluente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiluenteRepository extends JpaRepository<Diluente, Long> {

}
