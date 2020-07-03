package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.ClassificacaoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClassificacaoMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassificacaoMaterialRepository extends JpaRepository<ClassificacaoMaterial, Long> {
}
