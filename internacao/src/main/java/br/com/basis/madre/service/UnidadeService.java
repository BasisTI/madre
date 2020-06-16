package br.com.basis.madre.service;

import br.com.basis.madre.domain.Unidade;
import br.com.basis.madre.repository.UnidadeRepository;
import br.com.basis.madre.repository.search.UnidadeSearchRepository;
import br.com.basis.madre.service.dto.UnidadeDTO;
import br.com.basis.madre.service.mapper.UnidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Unidade}.
 */
@Service
@Transactional
public class UnidadeService {

    private final Logger log = LoggerFactory.getLogger(UnidadeService.class);

    private final UnidadeRepository unidadeRepository;

    private final UnidadeMapper unidadeMapper;

    private final UnidadeSearchRepository unidadeSearchRepository;

    public UnidadeService(UnidadeRepository unidadeRepository, UnidadeMapper unidadeMapper, UnidadeSearchRepository unidadeSearchRepository) {
        this.unidadeRepository = unidadeRepository;
        this.unidadeMapper = unidadeMapper;
        this.unidadeSearchRepository = unidadeSearchRepository;
    }

    /**
     * Save a unidade.
     *
     * @param unidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public UnidadeDTO save(UnidadeDTO unidadeDTO) {
        log.debug("Request to save Unidade : {}", unidadeDTO);
        Unidade unidade = unidadeMapper.toEntity(unidadeDTO);
        unidade = unidadeRepository.save(unidade);
        UnidadeDTO result = unidadeMapper.toDto(unidade);
        unidadeSearchRepository.save(unidade);
        return result;
    }

    /**
     * Get all the unidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Unidades");
        return unidadeRepository.findAll(pageable)
            .map(unidadeMapper::toDto);
    }


    /**
     * Get one unidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnidadeDTO> findOne(Long id) {
        log.debug("Request to get Unidade : {}", id);
        return unidadeRepository.findById(id)
            .map(unidadeMapper::toDto);
    }

    /**
     * Delete the unidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Unidade : {}", id);
        unidadeRepository.deleteById(id);
        unidadeSearchRepository.deleteById(id);
    }

    /**
     * Search for the unidade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Unidades for query {}", query);
        return unidadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(unidadeMapper::toDto);
    }
}
