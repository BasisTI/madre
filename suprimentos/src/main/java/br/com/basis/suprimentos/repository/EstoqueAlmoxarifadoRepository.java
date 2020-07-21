package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the EstoqueAlmoxarifado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstoqueAlmoxarifadoRepository extends JpaRepository<EstoqueAlmoxarifado, Long>, JpaSpecificationExecutor {
    Optional<EstoqueAlmoxarifado> findByAlmoxarifadoIdAndMaterialId(Long almoxarifadoId, Long materialId);

    <S extends EstoqueAlmoxarifado, T> Page<T> findBy(Example<S> example, Pageable pageable, Class<T> projectionClass);

    <T> Page<T> findBy(Pageable pageable, Class<T> projectionClass);
}
