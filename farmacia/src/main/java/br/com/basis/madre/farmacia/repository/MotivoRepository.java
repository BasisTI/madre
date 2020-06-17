package br.com.basis.madre.farmacia.repository;
import br.com.basis.madre.farmacia.domain.Motivo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Motivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotivoRepository extends JpaRepository<Motivo, Long> {

}
