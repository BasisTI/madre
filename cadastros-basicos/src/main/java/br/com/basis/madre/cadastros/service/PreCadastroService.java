package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.repository.PreCadastroRepository;
import br.com.basis.madre.cadastros.repository.search.PreCadastroSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PreCadastro.
 */
@Service
@Transactional
public class PreCadastroService {

    private final Logger log = LoggerFactory.getLogger(PreCadastroService.class);

    private final PreCadastroRepository preCadastroRepository;

    private final PreCadastroSearchRepository preCadastroSearchRepository;

    public PreCadastroService(PreCadastroRepository preCadastroRepository, PreCadastroSearchRepository preCadastroSearchRepository) {
        this.preCadastroRepository = preCadastroRepository;
        this.preCadastroSearchRepository = preCadastroSearchRepository;
    }

    /**
     * Save a preCadastro.
     *
     * @param preCadastro the entity to save
     * @return the persisted entity
     */
    public PreCadastro save(PreCadastro preCadastro) {
        log.debug("Request to save PreCadastro : {}", preCadastro);
        PreCadastro result = preCadastroRepository.save(preCadastro);
        preCadastroSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the preCadastros.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PreCadastro> findAll(Pageable pageable) {
        log.debug("Request to get all PreCadastros");
        return preCadastroRepository.findAll(pageable);
    }

    /**
     * Get one preCadastro by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PreCadastro findOne(Long id) {
        log.debug("Request to get PreCadastro : {}", id);
        return preCadastroRepository.findOne(id);
    }

    /**
     * Delete the preCadastro by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PreCadastro : {}", id);
        preCadastroRepository.delete(id);
        preCadastroSearchRepository.delete(id);
    }

    /**
     * Search for the preCadastro corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PreCadastro> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PreCadastros for query {}", query);
        Page<PreCadastro> result = preCadastroSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
