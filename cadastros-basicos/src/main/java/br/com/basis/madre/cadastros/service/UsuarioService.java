package br.com.basis.madre.cadastros.service;

import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.service.dto.UsuarioDTO;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.exception.UsuarioException;

//
//


/**
 * Service managing Usuario.
 */
public interface UsuarioService {


    /**
     * Save a Usuario.
     *
     * @param usuarioDTO the entity to save
     * @return the persisted entity
     */
    UsuarioDTO save(UsuarioDTO usuarioDTO) throws UsuarioException;

    /**
     * Get all the Usuario.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UsuarioDTO> findAll(Optional<String> query, Pageable pageable);

    /**
     * Get the "id" Usuario.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UsuarioDTO findOne(Long id);

    /**
     * Delete the "id" Usuario.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Page<Usuario> search(String query, Pageable pageable);

    ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio, String query) throws RelatorioException;

}

