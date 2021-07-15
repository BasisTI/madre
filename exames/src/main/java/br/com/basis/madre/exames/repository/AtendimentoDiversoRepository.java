package br.com.basis.madre.exames.repository;

import br.com.basis.madre.exames.domain.AtendimentoDiverso;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AtendimentoDiverso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AtendimentoDiversoRepository extends JpaRepository<AtendimentoDiverso, Long> {
}
