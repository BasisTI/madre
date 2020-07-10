package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.InformacaoTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InformacaoTransferencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformacaoTransferenciaRepository extends JpaRepository<InformacaoTransferencia, Long> {
}
