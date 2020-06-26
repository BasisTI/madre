package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.GrupoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GrupoMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoMaterialRepository extends JpaRepository<GrupoMaterial, Long> {

}
