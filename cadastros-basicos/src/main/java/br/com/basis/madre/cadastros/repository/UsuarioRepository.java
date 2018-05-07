package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.Usuario;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
//vali
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
//vali


/**
 * Spring Data JPA repository for the Usuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
