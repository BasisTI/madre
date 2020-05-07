package br.com.basis.madre.repository;
import br.com.basis.madre.domain.BloqueioDeLeito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BloqueioDeLeito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloqueioDeLeitoRepository extends JpaRepository<BloqueioDeLeito, Long> {

}
