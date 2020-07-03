package br.com.basis.madre.service;

import br.com.basis.madre.domain.Genitores;
import br.com.basis.madre.repository.GenitoresRepository;
import br.com.basis.madre.repository.search.GenitoresSearchRepository;
import br.com.basis.madre.service.dto.GenitoresDTO;
import br.com.basis.madre.service.mapper.GenitoresMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Genitores}.
 */
@Service
@Transactional
public class GenitoresService {

    private final Logger log = LoggerFactory.getLogger(GenitoresService.class);

    private final GenitoresRepository genitoresRepository;

    private final GenitoresMapper genitoresMapper;

    private final GenitoresSearchRepository genitoresSearchRepository;

    public GenitoresService(GenitoresRepository genitoresRepository, GenitoresMapper genitoresMapper, GenitoresSearchRepository genitoresSearchRepository) {
        this.genitoresRepository = genitoresRepository;
        this.genitoresMapper = genitoresMapper;
        this.genitoresSearchRepository = genitoresSearchRepository;
    }

    /**
     * Save a genitores.
     *
     * @param genitoresDTO the entity to save.
     * @return the persisted entity.
     */
    public GenitoresDTO save(GenitoresDTO genitoresDTO) {
        log.debug("Request to save Genitores : {}", genitoresDTO);
        Genitores genitores = genitoresMapper.toEntity(genitoresDTO);
        genitores = genitoresRepository.save(genitores);
        GenitoresDTO result = genitoresMapper.toDto(genitores);
        genitoresSearchRepository.save(genitores);
        return result;
    }

    /**
     * Get all the genitores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GenitoresDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Genitores");
        return genitoresRepository.findAll(pageable)
            .map(genitoresMapper::toDto);
    }

    /**
     * Get one genitores by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GenitoresDTO> findOne(Long id) {
        log.debug("Request to get Genitores : {}", id);
        return genitoresRepository.findById(id)
            .map(genitoresMapper::toDto);
    }

    /**
     * Delete the genitores by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Genitores : {}", id);
        genitoresRepository.deleteById(id);
        genitoresSearchRepository.deleteById(id);
    }

    /**
     * Search for the genitores corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GenitoresDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Genitores for query {}", query);
        return genitoresSearchRepository.search(queryStringQuery(query), pageable)
            .map(genitoresMapper::toDto);
    }
}
