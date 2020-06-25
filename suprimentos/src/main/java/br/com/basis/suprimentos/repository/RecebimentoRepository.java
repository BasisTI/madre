package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.Recebimento;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Recebimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Long> {
    <T, D> Page<T> findByNotaFiscalEntradaId(Long notaFiscalEntradaId, Pageable pageable, Class<T> projection);

    <T, D> Page<T> findBy(Pageable pageable, Class<T> projection);
}
