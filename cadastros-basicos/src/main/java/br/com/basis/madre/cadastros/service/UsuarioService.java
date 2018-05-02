package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.Usuario;
//
import br.com.basis.madre.cadastros.repository.UsuarioRepository;
import br.com.basis.madre.cadastros.repository.search.UsuarioSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//

import static org.elasticsearch.index.query.QueryBuilders.*;


/**
 * Service Implementation for managing Usuario.
 */
@Service
@Transactional
public class UsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioSearchRepository usuarioSearchRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioSearchRepository usuarioSearchRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioSearchRepository = usuarioSearchRepository;
    }

    /**
     * Save a usuario.
     *
     * @param usuario the entity to save
     * @return the persisted entity
     */
    public Usuario save(Usuario usuario) {
        log.debug("Request to save Usuario : {}", usuario);
        Usuario result = usuarioRepository.save(usuario);
        usuarioSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable);
    }

    /**
     * Get one usuario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Usuario findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findOne(id);
    }

    /**
     * Delete the usuario by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.delete(id);
        usuarioSearchRepository.delete(id);
    }

    /**
     * Search for the usuario corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Usuario> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Usuarios for query {}", query);
        Page<Usuario> result = usuarioSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
