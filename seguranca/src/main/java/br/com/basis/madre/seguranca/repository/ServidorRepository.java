package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Servidor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Servidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {
}
