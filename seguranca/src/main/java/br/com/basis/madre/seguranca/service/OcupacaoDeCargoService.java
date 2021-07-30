package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.OcupacaoDeCargo;
import br.com.basis.madre.seguranca.repository.OcupacaoDeCargoRepository;
import br.com.basis.madre.seguranca.repository.search.OcupacaoDeCargoSearchRepository;
import br.com.basis.madre.seguranca.service.dto.OcupacaoDeCargoDTO;
import br.com.basis.madre.seguranca.service.mapper.OcupacaoDeCargoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link OcupacaoDeCargo}.
 */
@Service
@Transactional
public class OcupacaoDeCargoService {

    private final Logger log = LoggerFactory.getLogger(OcupacaoDeCargoService.class);

    private final OcupacaoDeCargoRepository ocupacaoDeCargoRepository;

    private final OcupacaoDeCargoMapper ocupacaoDeCargoMapper;

    private final OcupacaoDeCargoSearchRepository ocupacaoDeCargoSearchRepository;

    public OcupacaoDeCargoService(OcupacaoDeCargoRepository ocupacaoDeCargoRepository, OcupacaoDeCargoMapper ocupacaoDeCargoMapper, OcupacaoDeCargoSearchRepository ocupacaoDeCargoSearchRepository) {
        this.ocupacaoDeCargoRepository = ocupacaoDeCargoRepository;
        this.ocupacaoDeCargoMapper = ocupacaoDeCargoMapper;
        this.ocupacaoDeCargoSearchRepository = ocupacaoDeCargoSearchRepository;
    }

    /**
     * Save a ocupacaoDeCargo.
     *
     * @param ocupacaoDeCargoDTO the entity to save.
     * @return the persisted entity.
     */
    public OcupacaoDeCargoDTO save(OcupacaoDeCargoDTO ocupacaoDeCargoDTO) {
        log.debug("Request to save OcupacaoDeCargo : {}", ocupacaoDeCargoDTO);
        OcupacaoDeCargo ocupacaoDeCargo = ocupacaoDeCargoMapper.toEntity(ocupacaoDeCargoDTO);
        ocupacaoDeCargo = ocupacaoDeCargoRepository.save(ocupacaoDeCargo);
        OcupacaoDeCargoDTO result = ocupacaoDeCargoMapper.toDto(ocupacaoDeCargo);
        ocupacaoDeCargoSearchRepository.save(ocupacaoDeCargo);
        return result;
    }

    /**
     * Get all the ocupacaoDeCargos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OcupacaoDeCargoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OcupacaoDeCargos");
        return ocupacaoDeCargoRepository.findAll(pageable)
            .map(ocupacaoDeCargoMapper::toDto);
    }


    /**
     * Get one ocupacaoDeCargo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OcupacaoDeCargoDTO> findOne(Long id) {
        log.debug("Request to get OcupacaoDeCargo : {}", id);
        return ocupacaoDeCargoRepository.findById(id)
            .map(ocupacaoDeCargoMapper::toDto);
    }

    /**
     * Delete the ocupacaoDeCargo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OcupacaoDeCargo : {}", id);
        ocupacaoDeCargoRepository.deleteById(id);
        ocupacaoDeCargoSearchRepository.deleteById(id);
    }

    /**
     * Search for the ocupacaoDeCargo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OcupacaoDeCargoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OcupacaoDeCargos for query {}", query);
        return ocupacaoDeCargoSearchRepository.search(queryStringQuery(query), pageable)
            .map(ocupacaoDeCargoMapper::toDto);
    }
}
