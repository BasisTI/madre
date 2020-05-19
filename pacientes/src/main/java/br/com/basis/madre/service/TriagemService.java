package br.com.basis.madre.service;

import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.domain.events.EventoTriagem;
import br.com.basis.madre.repository.TriagemRepository;
import br.com.basis.madre.repository.search.TriagemSearchRepository;
import br.com.basis.madre.service.dto.TriagemDTO;
import br.com.basis.madre.service.mapper.TriagemMapper;
import br.com.basis.madre.service.projection.TriagemProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.EmitterProcessor;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Triagem.
 */
@Service
@Transactional
public class TriagemService {

    private final Logger log = LoggerFactory.getLogger(TriagemService.class);

    private final TriagemRepository triagemRepository;

    private final TriagemMapper triagemMapper;

    private final TriagemSearchRepository triagemSearchRepository;

    private final EmitterProcessor<EventoTriagem> triagemEmitterProcessor;

    public TriagemService(TriagemRepository triagemRepository, TriagemMapper triagemMapper, TriagemSearchRepository triagemSearchRepository, EmitterProcessor<EventoTriagem> triagemEmitterProcessor) {
        this.triagemRepository = triagemRepository;
        this.triagemMapper = triagemMapper;
        this.triagemSearchRepository = triagemSearchRepository;
        this.triagemEmitterProcessor = triagemEmitterProcessor;
    }

    /**
     * Save a triagem.
     *
     * @param triagemDTO the entity to save
     * @return the persisted entity
     */

    public TriagemDTO save(TriagemDTO triagemDTO) {
        log.debug("Request to save Triagem : {}", triagemDTO);

        Triagem triagem = triagemMapper.toEntity(triagemDTO);
        triagem = triagemRepository.save(triagem);
        TriagemDTO result = triagemMapper.toDto(triagem);
        triagemSearchRepository.save(triagem);
        EventoTriagem eventoTriagem = new EventoTriagem(triagem);
        triagemEmitterProcessor.onNext(eventoTriagem);
        return result;
    }

    /**
     * Get all the triagems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TriagemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Triagems");
        return triagemRepository.findAll(pageable)
            .map(triagemMapper::toDto);
    }


    /**
     * Get one triagem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TriagemDTO> findOne(Long id) {
        log.debug("Request to get Triagem : {}", id);
        return triagemRepository.findById(id)
            .map(triagemMapper::toDto);
    }

    /**
     * Delete the triagem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Triagem : {}", id);
        triagemRepository.deleteById(id);
        triagemSearchRepository.deleteById(id);
    }

    /**
     * Search for the triagem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TriagemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Triagems for query {}", query);
        return triagemSearchRepository.search(queryStringQuery(query), pageable)
            .map(triagemMapper::toDto);
    }

    /**
     * TODO: Write documentation
     */

    public Page<TriagemProjection> findAllTriagemBy(Pageable pageable) {
        return triagemRepository.findAllTriagemBy(pageable);
    }

}
