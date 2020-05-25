package br.com.basis.madre.repository;

import br.com.basis.madre.domain.MotivoDoBloqueioDeLeito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MotivoDoBloqueioDeLeito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotivoDoBloqueioDeLeitoRepository extends JpaRepository<MotivoDoBloqueioDeLeito, Long> {

}
