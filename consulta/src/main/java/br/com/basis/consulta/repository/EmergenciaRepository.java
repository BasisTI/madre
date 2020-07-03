package br.com.basis.consulta.repository;

import br.com.basis.consulta.domain.Emergencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Emergencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmergenciaRepository extends JpaRepository<Emergencia, Long> {

}
