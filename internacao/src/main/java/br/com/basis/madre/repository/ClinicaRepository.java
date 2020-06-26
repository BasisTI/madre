package br.com.basis.madre.repository;


import br.com.basis.madre.domain.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Clinica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {

}
