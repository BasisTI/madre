package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//vali
//vali


/**
 * Spring Data JPA repository for the Usuario entity.
 */
//@SuppressWarnings("unused")
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findOneByLoginIgnoreCase(String login);
    Optional<Usuario> findOneByIdAndLoginIgnoreCase (Long id, String login);
    Optional<Usuario> findOneByEmailIgnoreCase(String email);
    Optional<Usuario> findOneByIdAndEmailIgnoreCase (Long id, String email);

}
