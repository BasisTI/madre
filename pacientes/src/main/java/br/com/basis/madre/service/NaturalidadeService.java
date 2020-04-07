package br.com.basis.madre.service;

import br.com.basis.madre.domain.Naturalidade;
import br.com.basis.madre.repository.NaturalidadeRepository;
import br.com.basis.madre.repository.search.NaturalidadeSearchRepository;
import br.com.basis.madre.service.dto.NaturalidadeDTO;
import br.com.basis.madre.service.mapper.NaturalidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Naturalidade}.
 */
@Service
@Transactional
public class NaturalidadeService {

    private final Logger log = LoggerFactory.getLogger(NaturalidadeService.class);

    private final NaturalidadeRepository naturalidadeRepository;

    private final NaturalidadeMapper naturalidadeMapper;

    private final NaturalidadeSearchRepository naturalidadeSearchRepository;

    public NaturalidadeService(NaturalidadeRepository naturalidadeRepository, NaturalidadeMapper naturalidadeMapper, NaturalidadeSearchRepository naturalidadeSearchRepository) {
        this.naturalidadeRepository = naturalidadeRepository;
        this.naturalidadeMapper = naturalidadeMapper;
        this.naturalidadeSearchRepository = naturalidadeSearchRepository;
    }

    /**
     * Save a naturalidade.
     *
     * @param naturalidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public NaturalidadeDTO save(NaturalidadeDTO naturalidadeDTO) {
        log.debug("Request to save Naturalidade : {}", naturalidadeDTO);
        Naturalidade naturalidade = naturalidadeMapper.toEntity(naturalidadeDTO);
        naturalidade = naturalidadeRepository.save(naturalidade);
        NaturalidadeDTO result = naturalidadeMapper.toDto(naturalidade);
        naturalidadeSearchRepository.save(naturalidade);
        return result;
    }

    /**
     * Get all the naturalidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NaturalidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Naturalidades");
        return naturalidadeRepository.findAll(pageable)
            .map(naturalidadeMapper::toDto);
    }

    /**
     * Get one naturalidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NaturalidadeDTO> findOne(Long id) {
        log.debug("Request to get Naturalidade : {}", id);
        return naturalidadeRepository.findById(id)
            .map(naturalidadeMapper::toDto);
    }

    /**
     * Delete the naturalidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Naturalidade : {}", id);
        naturalidadeRepository.deleteById(id);
        naturalidadeSearchRepository.deleteById(id);
    }

    /**
     * Search for the naturalidade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NaturalidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Naturalidades for query {}", query);
        return naturalidadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(naturalidadeMapper::toDto);
    }
}
