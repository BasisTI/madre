package br.com.basis.madre.service;

import br.com.basis.madre.domain.CEP;
import br.com.basis.madre.repository.CEPRepository;
import br.com.basis.madre.repository.search.CEPSearchRepository;
import br.com.basis.madre.service.dto.CEPDTO;
import br.com.basis.madre.service.mapper.CEPMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CEP}.
 */
@Service
@Transactional
public class CEPService {

    private final Logger log = LoggerFactory.getLogger(CEPService.class);

    private final CEPRepository cEPRepository;

    private final CEPMapper cEPMapper;

    private final CEPSearchRepository cEPSearchRepository;

    public CEPService(CEPRepository cEPRepository, CEPMapper cEPMapper, CEPSearchRepository cEPSearchRepository) {
        this.cEPRepository = cEPRepository;
        this.cEPMapper = cEPMapper;
        this.cEPSearchRepository = cEPSearchRepository;
    }

    /**
     * Save a cEP.
     *
     * @param cEPDTO the entity to save.
     * @return the persisted entity.
     */
    public CEPDTO save(CEPDTO cEPDTO) {
        log.debug("Request to save CEP : {}", cEPDTO);
        CEP cEP = cEPMapper.toEntity(cEPDTO);
        cEP = cEPRepository.save(cEP);
        CEPDTO result = cEPMapper.toDto(cEP);
        cEPSearchRepository.save(cEP);
        return result;
    }

    /**
     * Get all the cEPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEPDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEPS");
        return cEPRepository.findAll(pageable)
            .map(cEPMapper::toDto);
    }

    public Page<CEP> findAllCEP(String cep, Pageable pageable) {
        return cEPRepository.findByCepContainsIgnoreCase(cep,pageable);
    }


    /**
     * Get one cEP by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEPDTO> findOne(Long id) {
        log.debug("Request to get CEP : {}", id);
        return cEPRepository.findById(id)
            .map(cEPMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<CEPDTO> findOneByCep(String cep) {
        log.debug("Request to get CEP : {}", cep);
        return cEPRepository.findByCep(cep)
            .map(cEPMapper::toDto);
    }

    /**
     * Delete the cEP by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEP : {}", id);
        cEPRepository.deleteById(id);
        cEPSearchRepository.deleteById(id);
    }

    /**
     * Search for the cEP corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEPDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CEPS for query {}", query);
        return cEPSearchRepository.search(queryStringQuery(query), pageable)
            .map(cEPMapper::toDto);
    }
}
