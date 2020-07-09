package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.RequisicaoMaterial;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RequisicaoMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequisicaoMaterialRepository extends JpaRepository<RequisicaoMaterial, Long> {
}
