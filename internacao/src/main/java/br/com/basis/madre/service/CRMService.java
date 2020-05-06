package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.CRM;
import br.com.basis.madre.repository.CRMRepository;
import br.com.basis.madre.repository.search.CRMSearchRepository;
import br.com.basis.madre.service.dto.CrmDTO;
import br.com.basis.madre.service.mapper.CRMMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CRMService {

    private final Logger log = LoggerFactory.getLogger(CRMService.class);

    private final CRMRepository crmRepository;

    private final CRMMapper crmMapper;

    private final CRMSearchRepository crmSearchRepository;

    /**
     * Save a cRM.
     *
     * @param cRMDTO the entity to save.
     * @return the persisted entity.
     */
    public CrmDTO save(CrmDTO cRMDTO) {
        log.debug("Request to save CRM : {}", cRMDTO);
        CRM cRM = crmMapper.toEntity(cRMDTO);
        cRM = crmRepository.save(cRM);
        CrmDTO result = crmMapper.toDto(cRM);
        crmSearchRepository.save(cRM);
        return result;
    }

    /**
     * Get all the cRMS.
     *
     * @param crmDTO
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CrmDTO> findAll(CrmDTO crmDTO, Pageable pageable) {
        log.debug("Request to get all CRMS");
        return crmRepository.findAll(
            Example.of(crmMapper.toEntity(crmDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING))
            , pageable)
            .map(crmMapper::toDto);
    }


    /**
     * Get one cRM by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CrmDTO> findOne(Long id) {
        log.debug("Request to get CRM : {}", id);
        return crmRepository.findById(id)
            .map(crmMapper::toDto);
    }

    /**
     * Delete the cRM by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CRM : {}", id);
        crmRepository.deleteById(id);
        crmSearchRepository.deleteById(id);
    }

    /**
     * Search for the cRM corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CrmDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CRMS for query {}", query);
        return crmSearchRepository.search(queryStringQuery(query), pageable)
            .map(crmMapper::toDto);
    }

}
