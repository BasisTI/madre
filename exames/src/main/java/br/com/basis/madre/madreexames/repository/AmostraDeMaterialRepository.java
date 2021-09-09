package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.AmostraDeMaterial;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AmostraDeMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmostraDeMaterialRepository extends JpaRepository<AmostraDeMaterial, Long> {
}
