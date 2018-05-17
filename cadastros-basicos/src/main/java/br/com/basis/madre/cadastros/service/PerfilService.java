package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.Perfil;
import br.com.basis.madre.cadastros.repository.PerfilRepository;
import br.com.basis.madre.cadastros.repository.search.PerfilSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Perfil.
 */
@Service
@Transactional
public class PerfilService {

    private final Logger log = LoggerFactory.getLogger(PerfilService.class);

    private final PerfilRepository perfilRepository;


    private final PerfilSearchRepository perfilSearchRepository;

    public PerfilService(PerfilRepository perfilRepository, PerfilSearchRepository perfilSearchRepository) {
        this.perfilRepository = perfilRepository;
        this.perfilSearchRepository = perfilSearchRepository;
    }

    /**
     * Save a perfil.
     *
     * @param perfil the entity to save
     * @return the persisted entity
     */
    public Perfil save(Perfil perfil) {
        log.debug("Request to save Perfil : {}", perfil);
        perfil = perfilRepository.save(perfil);
        perfilSearchRepository.save(perfil);
        return perfil;
    }

    /**
     * Get all the perfils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Perfil> findAll(Pageable pageable) {
        log.debug("Request to get all Perfils");
        return perfilRepository.findAll(pageable);
    }

    /**
     * Get one perfil by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Perfil findOne(Long id) {
        log.debug("Request to get Perfil : {}", id);
        return perfilRepository.findOne(id);
    }

    /**
     * Delete the perfil by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Perfil : {}", id);
        perfilRepository.delete(id);
        perfilSearchRepository.delete(id);
    }

    /**
     * Search for the perfil corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Perfil> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Perfils for query {}", query);
        return perfilSearchRepository.search(queryStringQuery(query), pageable);
    }
}
