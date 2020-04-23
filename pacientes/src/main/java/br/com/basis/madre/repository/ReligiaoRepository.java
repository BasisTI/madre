package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Religiao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Religiao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReligiaoRepository extends JpaRepository<Religiao, Long> {
}
