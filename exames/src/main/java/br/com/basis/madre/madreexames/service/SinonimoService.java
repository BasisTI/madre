package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Sinonimo;
import br.com.basis.madre.madreexames.repository.SinonimoRepository;
import br.com.basis.madre.madreexames.repository.search.SinonimoSearchRepository;
import br.com.basis.madre.madreexames.service.dto.SinonimoDTO;
import br.com.basis.madre.madreexames.service.mapper.SinonimoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Sinonimo}.
 */
@Service
@Transactional
public class SinonimoService {

    private final Logger log = LoggerFactory.getLogger(SinonimoService.class);

    private final SinonimoRepository sinonimoRepository;

    private final SinonimoMapper sinonimoMapper;

    private final SinonimoSearchRepository sinonimoSearchRepository;

    public SinonimoService(SinonimoRepository sinonimoRepository, SinonimoMapper sinonimoMapper, SinonimoSearchRepository sinonimoSearchRepository) {
        this.sinonimoRepository = sinonimoRepository;
        this.sinonimoMapper = sinonimoMapper;
        this.sinonimoSearchRepository = sinonimoSearchRepository;
    }

    /**
     * Save a sinonimo.
     *
     * @param sinonimoDTO the entity to save.
     * @return the persisted entity.
     */
    public SinonimoDTO save(SinonimoDTO sinonimoDTO) {
        log.debug("Request to save Sinonimo : {}", sinonimoDTO);
        Sinonimo sinonimo = sinonimoMapper.toEntity(sinonimoDTO);
        sinonimo = sinonimoRepository.save(sinonimo);
        SinonimoDTO result = sinonimoMapper.toDto(sinonimo);
        sinonimoSearchRepository.save(sinonimo);
        return result;
    }

    /**
     * Get all the sinonimos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SinonimoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sinonimos");
        return sinonimoRepository.findAll(pageable)
            .map(sinonimoMapper::toDto);
    }


    /**
     * Get one sinonimo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SinonimoDTO> findOne(Long id) {
        log.debug("Request to get Sinonimo : {}", id);
        return sinonimoRepository.findById(id)
            .map(sinonimoMapper::toDto);
    }

    /**
     * Delete the sinonimo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sinonimo : {}", id);
        sinonimoRepository.deleteById(id);
        sinonimoSearchRepository.deleteById(id);
    }

    /**
     * Search for the sinonimo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SinonimoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sinonimos for query {}", query);
        return sinonimoSearchRepository.search(queryStringQuery(query), pageable)
            .map(sinonimoMapper::toDto);
    }
}
