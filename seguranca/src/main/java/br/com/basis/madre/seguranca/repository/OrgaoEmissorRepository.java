package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.OrgaoEmissor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrgaoEmissor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgaoEmissorRepository extends JpaRepository<OrgaoEmissor, Long> {
}
