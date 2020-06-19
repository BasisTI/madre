package br.com.basis.suprimentos.repository;
import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransferenciaAlmoxarifado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferenciaAlmoxarifadoRepository extends JpaRepository<TransferenciaAlmoxarifado, Long> {

}
