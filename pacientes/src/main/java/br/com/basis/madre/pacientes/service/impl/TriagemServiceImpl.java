package br.com.basis.madre.pacientes.service.impl;

import br.com.basis.madre.pacientes.service.TriagemService;
import br.com.basis.madre.pacientes.domain.Triagem;
import br.com.basis.madre.pacientes.repository.TriagemRepository;
import br.com.basis.madre.pacientes.repository.search.TriagemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Triagem.
 */
@Service
@Transactional
public class TriagemServiceImpl implements TriagemService {

    private final Logger log = LoggerFactory.getLogger(TriagemServiceImpl.class);

    private final TriagemRepository triagemRepository;

    private final TriagemSearchRepository triagemSearchRepository;

    public TriagemServiceImpl(TriagemRepository triagemRepository, TriagemSearchRepository triagemSearchRepository) {
        this.triagemRepository = triagemRepository;
        this.triagemSearchRepository = triagemSearchRepository;
    }

    /**
     * Save a triagem.
     *
     * @param triagem the entity to save
     * @return the persisted entity
     */
    @Override
    public Triagem save(Triagem triagem) {
        log.debug("Request to save Triagem : {}", triagem);
        Triagem result = triagemRepository.save(triagem);
        triagemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the triagems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Triagem> findAll(Pageable pageable) {
        log.debug("Request to get all Triagems");
        return triagemRepository.findAll(pageable);
    }

    /**
     * Get one triagem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Triagem findOne(Long id) {
        log.debug("Request to get Triagem : {}", id);
        return triagemRepository.findOne(id);
    }

    /**
     * Delete the triagem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Triagem : {}", id);
        triagemRepository.delete(id);
        triagemSearchRepository.delete(id);
    }

    /**
     * Search for the triagem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Triagem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Triagems for query {}", query);
        Page<Triagem> result = triagemSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
