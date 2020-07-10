package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the TransferenciaAlmoxarifado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferenciaAlmoxarifadoRepository extends JpaRepository<TransferenciaAlmoxarifado, Long> {
    <T> Optional<T> findOneById(Long id, Class<T> projectionClass);

    <T> Page<T> findBy(Class<T> projectionClass, Pageable pageable);

    <T> Page<T> findByInformacaoTransferenciaEfetivada(Boolean ativa, Class<T> projectionClass, Pageable pageable);
}
