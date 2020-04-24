package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.CRM;
import br.com.basis.madre.repository.CRMRepository;
import br.com.basis.madre.repository.search.CRMSearchRepository;
import br.com.basis.madre.service.dto.CRMDTO;
import br.com.basis.madre.service.mapper.CRMMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing CRM.
 */
@Service
@Transactional
public class CRMService {

    private final Logger log = LoggerFactory.getLogger(CRMService.class);

    private final CRMRepository cRMRepository;

    private final CRMMapper cRMMapper;

    private final CRMSearchRepository cRMSearchRepository;

    public CRMService(CRMRepository cRMRepository, CRMMapper cRMMapper,
        CRMSearchRepository cRMSearchRepository) {
        this.cRMRepository = cRMRepository;
        this.cRMMapper = cRMMapper;
        this.cRMSearchRepository = cRMSearchRepository;
    }

    /**
     * Save a cRM.
     *
     * @param cRMDTO the entity to save
     * @return the persisted entity
     */
    public CRMDTO save(CRMDTO cRMDTO) {
        log.debug("Request to save CRM : {}", cRMDTO);

        CRM cRM = cRMMapper.toEntity(cRMDTO);
        cRM = cRMRepository.save(cRM);
        CRMDTO result = cRMMapper.toDto(cRM);
        cRMSearchRepository.save(cRM);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<CRMDTO> findAll(CRMDTO crmDTO, Pageable pageable) {
        log.debug("Request to get all CRMS");
        return cRMRepository.findAll(
            Example.of(cRMMapper.toEntity(crmDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING))
            , pageable)
            .map(cRMMapper::toDto);
    }

    /**
     * Get all the cRMS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CRMDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CRMS");
        return cRMRepository.findAll(pageable)
            .map(cRMMapper::toDto);
    }


    /**
     * Get one cRM by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CRMDTO> findOne(Long id) {
        log.debug("Request to get CRM : {}", id);
        return cRMRepository.findById(id)
            .map(cRMMapper::toDto);
    }

    /**
     * Delete the cRM by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CRM : {}", id);
        cRMRepository.deleteById(id);
        cRMSearchRepository.deleteById(id);
    }

    /**
     * Search for the cRM corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CRMDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CRMS for query {}", query);
        return cRMSearchRepository.search(queryStringQuery(query), pageable)
            .map(cRMMapper::toDto);
    }
}
