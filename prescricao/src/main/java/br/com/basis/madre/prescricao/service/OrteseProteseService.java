package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.OrteseProtese;
import br.com.basis.madre.prescricao.repository.OrteseProteseRepository;
import br.com.basis.madre.prescricao.repository.search.OrteseProteseSearchRepository;
import br.com.basis.madre.prescricao.service.dto.OrteseProteseDTO;
import br.com.basis.madre.prescricao.service.mapper.OrteseProteseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link OrteseProtese}.
 */
@Service
@Transactional
public class OrteseProteseService {

    private final Logger log = LoggerFactory.getLogger(OrteseProteseService.class);

    private final OrteseProteseRepository orteseProteseRepository;

    private final OrteseProteseMapper orteseProteseMapper;

    private final OrteseProteseSearchRepository orteseProteseSearchRepository;

    public OrteseProteseService(OrteseProteseRepository orteseProteseRepository, OrteseProteseMapper orteseProteseMapper, OrteseProteseSearchRepository orteseProteseSearchRepository) {
        this.orteseProteseRepository = orteseProteseRepository;
        this.orteseProteseMapper = orteseProteseMapper;
        this.orteseProteseSearchRepository = orteseProteseSearchRepository;
    }

    /**
     * Save a orteseProtese.
     *
     * @param orteseProteseDTO the entity to save.
     * @return the persisted entity.
     */
    public OrteseProteseDTO save(OrteseProteseDTO orteseProteseDTO) {
        log.debug("Request to save OrteseProtese : {}", orteseProteseDTO);
        OrteseProtese orteseProtese = orteseProteseMapper.toEntity(orteseProteseDTO);
        orteseProtese = orteseProteseRepository.save(orteseProtese);
        OrteseProteseDTO result = orteseProteseMapper.toDto(orteseProtese);
        orteseProteseSearchRepository.save(orteseProtese);
        return result;
    }

    /**
     * Get all the orteseProtese.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrteseProteseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrteseProtese");
        return orteseProteseRepository.findAll(pageable)
            .map(orteseProteseMapper::toDto);
    }


    /**
     * Get one orteseProtese by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrteseProteseDTO> findOne(Long id) {
        log.debug("Request to get OrteseProtese : {}", id);
        return orteseProteseRepository.findById(id)
            .map(orteseProteseMapper::toDto);
    }

    /**
     * Delete the orteseProtese by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrteseProtese : {}", id);
        orteseProteseRepository.deleteById(id);
        orteseProteseSearchRepository.deleteById(id);
    }

    /**
     * Search for the orteseProtese corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrteseProteseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrteseProtese for query {}", query);
        return orteseProteseSearchRepository.search(queryStringQuery(query), pageable)
            .map(orteseProteseMapper::toDto);
    }
}
