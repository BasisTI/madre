package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.UnidadeInfusao;
import br.com.basis.madre.prescricao.repository.UnidadeInfusaoRepository;
import br.com.basis.madre.prescricao.repository.search.UnidadeInfusaoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.UnidadeInfusaoDTO;
import br.com.basis.madre.prescricao.service.mapper.UnidadeInfusaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UnidadeInfusao}.
 */
@Service
@Transactional
public class UnidadeInfusaoService {

    private final Logger log = LoggerFactory.getLogger(UnidadeInfusaoService.class);

    private final UnidadeInfusaoRepository unidadeInfusaoRepository;

    private final UnidadeInfusaoMapper unidadeInfusaoMapper;

    private final UnidadeInfusaoSearchRepository unidadeInfusaoSearchRepository;

    public UnidadeInfusaoService(UnidadeInfusaoRepository unidadeInfusaoRepository, UnidadeInfusaoMapper unidadeInfusaoMapper, UnidadeInfusaoSearchRepository unidadeInfusaoSearchRepository) {
        this.unidadeInfusaoRepository = unidadeInfusaoRepository;
        this.unidadeInfusaoMapper = unidadeInfusaoMapper;
        this.unidadeInfusaoSearchRepository = unidadeInfusaoSearchRepository;
    }

    /**
     * Save a unidadeInfusao.
     *
     * @param unidadeInfusaoDTO the entity to save.
     * @return the persisted entity.
     */
    public UnidadeInfusaoDTO save(UnidadeInfusaoDTO unidadeInfusaoDTO) {
        log.debug("Request to save UnidadeInfusao : {}", unidadeInfusaoDTO);
        UnidadeInfusao unidadeInfusao = unidadeInfusaoMapper.toEntity(unidadeInfusaoDTO);
        unidadeInfusao = unidadeInfusaoRepository.save(unidadeInfusao);
        UnidadeInfusaoDTO result = unidadeInfusaoMapper.toDto(unidadeInfusao);
        unidadeInfusaoSearchRepository.save(unidadeInfusao);
        return result;
    }

    /**
     * Get all the unidadeInfusaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeInfusaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadeInfusaos");
        return unidadeInfusaoRepository.findAll(pageable)
            .map(unidadeInfusaoMapper::toDto);
    }


    /**
     * Get one unidadeInfusao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnidadeInfusaoDTO> findOne(Long id) {
        log.debug("Request to get UnidadeInfusao : {}", id);
        return unidadeInfusaoRepository.findById(id)
            .map(unidadeInfusaoMapper::toDto);
    }

    /**
     * Delete the unidadeInfusao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UnidadeInfusao : {}", id);
        unidadeInfusaoRepository.deleteById(id);
        unidadeInfusaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the unidadeInfusao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeInfusaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnidadeInfusaos for query {}", query);
        return unidadeInfusaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(unidadeInfusaoMapper::toDto);
    }
}
