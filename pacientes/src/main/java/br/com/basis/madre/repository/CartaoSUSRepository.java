package br.com.basis.madre.repository;

import br.com.basis.madre.domain.CartaoSUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CartaoSUS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartaoSUSRepository extends JpaRepository<CartaoSUS, Long> {
}
