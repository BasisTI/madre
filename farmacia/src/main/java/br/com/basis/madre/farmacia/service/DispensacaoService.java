package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.Dispensacao;
import br.com.basis.madre.farmacia.repository.DispensacaoRepository;
import br.com.basis.madre.farmacia.repository.search.DispensacaoSearchRepository;
import br.com.basis.madre.farmacia.service.dto.DispensacaoDTO;
import br.com.basis.madre.farmacia.service.mapper.DispensacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Dispensacao}.
 */
@Service
@Transactional
public class DispensacaoService {

    private final Logger log = LoggerFactory.getLogger(DispensacaoService.class);

    private final DispensacaoRepository dispensacaoRepository;

    private final DispensacaoMapper dispensacaoMapper;

    private final DispensacaoSearchRepository dispensacaoSearchRepository;

    public DispensacaoService(DispensacaoRepository dispensacaoRepository, DispensacaoMapper dispensacaoMapper, DispensacaoSearchRepository dispensacaoSearchRepository) {
        this.dispensacaoRepository = dispensacaoRepository;
        this.dispensacaoMapper = dispensacaoMapper;
        this.dispensacaoSearchRepository = dispensacaoSearchRepository;
    }

    /**
     * Save a dispensacao.
     *
     * @param dispensacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public DispensacaoDTO save(DispensacaoDTO dispensacaoDTO) {
        log.debug("Request to save Dispensacao : {}", dispensacaoDTO);
        Dispensacao dispensacao = dispensacaoMapper.toEntity(dispensacaoDTO);
        dispensacao = dispensacaoRepository.save(dispensacao);
        DispensacaoDTO result = dispensacaoMapper.toDto(dispensacao);
        dispensacaoSearchRepository.save(dispensacao);
        return result;
    }

    /**
     * Get all the dispensacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DispensacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dispensacaos");
        return dispensacaoRepository.findAll(pageable)
            .map(dispensacaoMapper::toDto);
    }


    /**
     * Get one dispensacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DispensacaoDTO> findOne(Long id) {
        log.debug("Request to get Dispensacao : {}", id);
        return dispensacaoRepository.findById(id)
            .map(dispensacaoMapper::toDto);
    }

    /**
     * Delete the dispensacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dispensacao : {}", id);
        dispensacaoRepository.deleteById(id);
        dispensacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the dispensacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DispensacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dispensacaos for query {}", query);
        return dispensacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(dispensacaoMapper::toDto);
    }
}
