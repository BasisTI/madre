package br.com.basis.madre.service;

import br.com.basis.madre.domain.TipoUnidade;
import br.com.basis.madre.repository.TipoUnidadeRepository;
import br.com.basis.madre.repository.search.TipoUnidadeSearchRepository;
import br.com.basis.madre.service.dto.TipoUnidadeDTO;
import br.com.basis.madre.service.mapper.TipoUnidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoUnidade}.
 */
@Service
@Transactional
public class TipoUnidadeService {

    private final Logger log = LoggerFactory.getLogger(TipoUnidadeService.class);

    private final TipoUnidadeRepository tipoUnidadeRepository;

    private final TipoUnidadeMapper tipoUnidadeMapper;

    private final TipoUnidadeSearchRepository tipoUnidadeSearchRepository;

    public TipoUnidadeService(TipoUnidadeRepository tipoUnidadeRepository, TipoUnidadeMapper tipoUnidadeMapper, TipoUnidadeSearchRepository tipoUnidadeSearchRepository) {
        this.tipoUnidadeRepository = tipoUnidadeRepository;
        this.tipoUnidadeMapper = tipoUnidadeMapper;
        this.tipoUnidadeSearchRepository = tipoUnidadeSearchRepository;
    }

    /**
     * Save a tipoUnidade.
     *
     * @param tipoUnidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoUnidadeDTO save(TipoUnidadeDTO tipoUnidadeDTO) {
        log.debug("Request to save TipoUnidade : {}", tipoUnidadeDTO);
        TipoUnidade tipoUnidade = tipoUnidadeMapper.toEntity(tipoUnidadeDTO);
        tipoUnidade = tipoUnidadeRepository.save(tipoUnidade);
        TipoUnidadeDTO result = tipoUnidadeMapper.toDto(tipoUnidade);
        tipoUnidadeSearchRepository.save(tipoUnidade);
        return result;
    }

    /**
     * Get all the tipoUnidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoUnidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoUnidades");
        return tipoUnidadeRepository.findAll(pageable)
            .map(tipoUnidadeMapper::toDto);
    }


    /**
     * Get one tipoUnidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoUnidadeDTO> findOne(Long id) {
        log.debug("Request to get TipoUnidade : {}", id);
        return tipoUnidadeRepository.findById(id)
            .map(tipoUnidadeMapper::toDto);
    }

    /**
     * Delete the tipoUnidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoUnidade : {}", id);
        tipoUnidadeRepository.deleteById(id);
        tipoUnidadeSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoUnidade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoUnidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoUnidades for query {}", query);
        return tipoUnidadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoUnidadeMapper::toDto);
    }
}
