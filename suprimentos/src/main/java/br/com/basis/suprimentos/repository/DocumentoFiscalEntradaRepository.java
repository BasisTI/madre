package br.com.basis.suprimentos.repository;
import br.com.basis.suprimentos.domain.DocumentoFiscalEntrada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentoFiscalEntrada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoFiscalEntradaRepository extends JpaRepository<DocumentoFiscalEntrada, Long> {

}
