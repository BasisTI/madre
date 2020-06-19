package br.com.basis.suprimentos.repository;
import br.com.basis.suprimentos.domain.MarcaComercial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MarcaComercial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarcaComercialRepository extends JpaRepository<MarcaComercial, Long> {

}
