package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Graduacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Graduacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GraduacaoRepository extends JpaRepository<Graduacao, Long> {
}
