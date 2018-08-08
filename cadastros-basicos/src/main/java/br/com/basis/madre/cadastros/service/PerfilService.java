package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.PerfilDTO;
import br.com.basis.madre.cadastros.domain.Perfil;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * Service Interface for managing Perfil.
 */
public interface PerfilService {

    /**
     * Save a perfil.
     *
     * @param perfil the entity to save
     * @return the persisted entity
     */
    Perfil save(Perfil perfil);

    /**
     * Get all the perfils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Perfil> findAll(Pageable pageable);

    /**
     * Get the "id" perfil.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Perfil findOne(Long id);

    /**
     * Delete the "id" perfil.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the perfil corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Perfil> search(String query, Pageable pageable);
    
    Perfil convertAcaoTempToPerfil(PerfilDTO acaoTemp);
    Perfil convertAcaoTempToPerfil(PerfilDTO acaoTemp, String tipo);

    ResponseEntity<InputStreamResource> gerarRelatorioExportacao (String tipoRelatorio, String querry) throws RelatorioException;
}
