package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.GrupoFuncional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GrupoFuncional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoFuncionalRepository extends JpaRepository<GrupoFuncional, Long> {
}
