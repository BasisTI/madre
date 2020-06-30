package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Certidao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Certidao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertidaoRepository extends JpaRepository<Certidao, Long> {
}
