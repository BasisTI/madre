package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.UnidadeDose;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnidadeDose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeDoseRepository extends JpaRepository<UnidadeDose, Long> {

}
