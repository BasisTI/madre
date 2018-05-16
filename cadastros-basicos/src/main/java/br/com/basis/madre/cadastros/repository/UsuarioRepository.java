package br.com.basis.madre.cadastros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.cadastros.domain.Usuario;

//vali
//vali


/**
 * Spring Data JPA repository for the Usuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findOneByLogin(String login);
    Optional<Usuario> findOneByNome(String nome);
    Optional<Usuario> findOneByEmail(String email);
}
