package br.com.basis.madre.repository;

import br.com.basis.madre.domain.TipoUnidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoUnidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoUnidadeRepository extends JpaRepository<TipoUnidade, Long> {

}
