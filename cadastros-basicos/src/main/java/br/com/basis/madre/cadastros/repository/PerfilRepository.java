package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Perfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
