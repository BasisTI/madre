package br.com.basis.madre.service;

import br.com.basis.madre.domain.CID;
import br.com.basis.madre.repository.CIDRepository;
import br.com.basis.madre.repository.search.CIDSearchRepository;
import br.com.basis.madre.service.dto.CIDDTO;
import br.com.basis.madre.service.mapper.CIDMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CID.
 */
@Service
@Transactional
public class CIDService {

    private final Logger log = LoggerFactory.getLogger(CIDService.class);

    private final CIDRepository cIDRepository;

    private final CIDMapper cIDMapper;

    private final CIDSearchRepository cIDSearchRepository;

    public CIDService(CIDRepository cIDRepository, CIDMapper cIDMapper, CIDSearchRepository cIDSearchRepository) {
        this.cIDRepository = cIDRepository;
        this.cIDMapper = cIDMapper;
        this.cIDSearchRepository = cIDSearchRepository;
    }

    /**
     * Save a cID.
     *
     * @param cIDDTO the entity to save
     * @return the persisted entity
     */
    public CIDDTO save(CIDDTO cIDDTO) {
        log.debug("Request to save CID : {}", cIDDTO);

        CID cID = cIDMapper.toEntity(cIDDTO);
        cID = cIDRepository.save(cID);
        CIDDTO result = cIDMapper.toDto(cID);
        cIDSearchRepository.save(cID);
        return result;
    }

    /**
     * Get all the cIDS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CIDDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CIDS");
        return cIDRepository.findAll(pageable)
            .map(cIDMapper::toDto);
    }


    /**
     * Get one cID by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CIDDTO> findOne(Long id) {
        log.debug("Request to get CID : {}", id);
        return cIDRepository.findById(id)
            .map(cIDMapper::toDto);
    }

    /**
     * Delete the cID by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CID : {}", id);
        cIDRepository.deleteById(id);
        cIDSearchRepository.deleteById(id);
    }

    /**
     * Search for the cID corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CIDDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CIDS for query {}", query);
        return cIDSearchRepository.search(queryStringQuery(query), pageable)
            .map(cIDMapper::toDto);
    }
}
