package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.Usuario;
import br.com.basis.madre.seguranca.repository.UsuarioRepository;
import br.com.basis.madre.seguranca.repository.search.UsuarioSearchRepository;
import br.com.basis.madre.seguranca.service.dto.MensagemDeLoginDTO;
import br.com.basis.madre.seguranca.service.dto.UsuarioDTO;
import br.com.basis.madre.seguranca.service.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Usuario}.
 */
@Service
@Transactional
public class UsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final UsuarioSearchRepository usuarioSearchRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, UsuarioSearchRepository usuarioSearchRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.usuarioSearchRepository = usuarioSearchRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        log.debug("Request to save Usuario : {}", usuarioDTO);
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        UsuarioDTO result = usuarioMapper.toDto(usuario);
        usuarioSearchRepository.save(usuario);
        return result;
    }

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable)
            .map(usuarioMapper::toDto);
    }


    /**
     * Get one usuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findById(id)
            .map(usuarioMapper::toDto);
    }

    /**
     * Delete the usuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
        usuarioSearchRepository.deleteById(id);
    }

    /**
     * Search for the usuario corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Usuarios for query {}", query);
        return usuarioSearchRepository.search(queryStringQuery(query), pageable)
            .map(usuarioMapper::toDto);
    }

    public MensagemDeLoginDTO validaSenha(UsuarioDTO usuarioDTO) {
        MensagemDeLoginDTO mensagemDeLoginDTO = new MensagemDeLoginDTO();
        Optional<Usuario> usuario = usuarioRepository.findByLogin(usuarioDTO.getLogin());
        if(!usuario.isPresent()){
            mensagemDeLoginDTO.setMsgDeErro("Usuario inválido");
            mensagemDeLoginDTO.setAutenticado(false);
        } else {
            boolean autenticado = passwordEncoder.matches(usuario.get().getSenha(), usuarioDTO.getSenha());
           if(autenticado) {
               mensagemDeLoginDTO.setAutenticado(autenticado);
           } else {
               mensagemDeLoginDTO.setMsgDeErro("Usuário ou senha inválido");
               mensagemDeLoginDTO.setAutenticado(autenticado);
           }
        }
        return mensagemDeLoginDTO;
    }
}
