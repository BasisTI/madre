package br.com.basis.consulta.repository;

import br.com.basis.consulta.domain.Emergencia;
import br.com.basis.consulta.service.projection.CalendarioResumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Emergencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmergenciaRepository extends JpaRepository<Emergencia, Long> {
    Page<CalendarioResumo> findAllCalendarioResumoBy(Pageable pageable);

    Long countAllByIdIsNotNull();
}
