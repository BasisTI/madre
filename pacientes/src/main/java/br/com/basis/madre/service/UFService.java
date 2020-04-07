package br.com.basis.madre.service;

import br.com.basis.madre.domain.UF;
import br.com.basis.madre.repository.UFRepository;
import br.com.basis.madre.repository.search.UFSearchRepository;
import br.com.basis.madre.service.dto.UFDTO;
import br.com.basis.madre.service.mapper.UFMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UF}.
 */
@Service
@Transactional
public class UFService {

    private final Logger log = LoggerFactory.getLogger(UFService.class);

    private final UFRepository uFRepository;

    private final UFMapper uFMapper;

    private final UFSearchRepository uFSearchRepository;

    public UFService(UFRepository uFRepository, UFMapper uFMapper, UFSearchRepository uFSearchRepository) {
        this.uFRepository = uFRepository;
        this.uFMapper = uFMapper;
        this.uFSearchRepository = uFSearchRepository;
    }

    /**
     * Save a uF.
     *
     * @param uFDTO the entity to save.
     * @return the persisted entity.
     */
    public UFDTO save(UFDTO uFDTO) {
        log.debug("Request to save UF : {}", uFDTO);
        UF uF = uFMapper.toEntity(uFDTO);
        uF = uFRepository.save(uF);
        UFDTO result = uFMapper.toDto(uF);
        uFSearchRepository.save(uF);
        return result;
    }

    /**
     * Get all the uFS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UFDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UFS");
        return uFRepository.findAll(pageable)
            .map(uFMapper::toDto);
    }

    /**
     * Get one uF by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UFDTO> findOne(Long id) {
        log.debug("Request to get UF : {}", id);
        return uFRepository.findById(id)
            .map(uFMapper::toDto);
    }

    /**
     * Delete the uF by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UF : {}", id);
        uFRepository.deleteById(id);
        uFSearchRepository.deleteById(id);
    }

    /**
     * Search for the uF corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UFDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UFS for query {}", query);
        return uFSearchRepository.search(queryStringQuery(query), pageable)
            .map(uFMapper::toDto);
    }
}
