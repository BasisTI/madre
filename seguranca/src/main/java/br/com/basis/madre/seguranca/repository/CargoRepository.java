package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Cargo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cargo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
