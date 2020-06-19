package br.com.basis.consulta.repository;
import br.com.basis.consulta.domain.Entidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Entidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadeRepository extends JpaRepository<Entidade, Long> {

}
