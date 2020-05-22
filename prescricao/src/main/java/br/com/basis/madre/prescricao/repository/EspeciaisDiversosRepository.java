package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.EspeciaisDiversos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EspeciaisDiversos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspeciaisDiversosRepository extends JpaRepository<EspeciaisDiversos, Long> {

}
