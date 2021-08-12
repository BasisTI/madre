package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Documentos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Documentos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Long> {
}
