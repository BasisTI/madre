package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.TipoUnidadeDieta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoUnidadeDieta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoUnidadeDietaRepository extends JpaRepository<TipoUnidadeDieta, Long> {

}
