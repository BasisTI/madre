package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransferenciaAlmoxarifado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferenciaAlmoxarifadoRepository extends JpaRepository<TransferenciaAlmoxarifado, Long> {
    <T> Page<T> findBy(Class<T> projectionClass, Pageable pageable);

    <T> Page<T> findByInformacaoTransferenciaEfetivada(Boolean ativa, Class<T> projectionClass, Pageable pageable);
}
