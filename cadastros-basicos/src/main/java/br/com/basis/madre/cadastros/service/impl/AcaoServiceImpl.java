package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.service.AcaoService;
import br.com.basis.madre.cadastros.domain.Acao;
import br.com.basis.madre.cadastros.repository.AcaoRepository;
import br.com.basis.madre.cadastros.repository.search.AcaoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Acao.
 */
@Service
@Transactional
public class AcaoServiceImpl implements AcaoService {

    private final Logger log = LoggerFactory.getLogger(AcaoServiceImpl.class);

    private final AcaoRepository acaoRepository;

    private final AcaoSearchRepository acaoSearchRepository;

    public AcaoServiceImpl(AcaoRepository acaoRepository, AcaoSearchRepository acaoSearchRepository) {
        this.acaoRepository = acaoRepository;
        this.acaoSearchRepository = acaoSearchRepository;
    }

    /**
     * Save a acao.
     *
     * @param acao the entity to save
     * @return the persisted entity
     */
    @Override
    public Acao save(Acao acao) {
        log.debug("Request to save Acao : {}", acao);
        Acao result = acaoRepository.save(acao);
        acaoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the acaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Acao> findAll(Pageable pageable) {
        log.debug("Request to get all Acaos");
        return acaoRepository.findAll(pageable);
    }

    /**
     * Get one acao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Acao findOne(Long id) {
        log.debug("Request to get Acao : {}", id);
        return acaoRepository.findOne(id);
    }

    /**
     * Delete the acao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Acao : {}", id);
        acaoRepository.delete(id);
        acaoSearchRepository.delete(id);
    }

    /**
     * Search for the acao corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Acao> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Acaos for query {}", query);
        return acaoSearchRepository.search(queryStringQuery(query), pageable);
    }
}
