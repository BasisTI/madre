package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.TipoItemDieta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoItemDieta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoItemDietaRepository extends JpaRepository<TipoItemDieta, Long> {

}
