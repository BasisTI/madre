package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Servidor;

import br.com.basis.madre.seguranca.service.projection.ServidorResumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Servidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {
    Page<ServidorResumo> findAllProjectedServidorResumoByMatriculaContainingIgnoreCase(String matricula, Pageable pageable);
}
