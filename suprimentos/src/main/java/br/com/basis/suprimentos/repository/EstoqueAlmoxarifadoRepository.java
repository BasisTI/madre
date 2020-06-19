package br.com.basis.suprimentos.repository;
import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstoqueAlmoxarifado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstoqueAlmoxarifadoRepository extends JpaRepository<EstoqueAlmoxarifado, Long> {

}
