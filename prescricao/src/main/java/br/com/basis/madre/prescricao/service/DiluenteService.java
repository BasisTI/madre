package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.Diluente;
import br.com.basis.madre.prescricao.repository.DiluenteRepository;
import br.com.basis.madre.prescricao.repository.search.DiluenteSearchRepository;
import br.com.basis.madre.prescricao.service.dto.DiluenteDTO;
import br.com.basis.madre.prescricao.service.mapper.DiluenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Diluente}.
 */
@Service
@Transactional
public class DiluenteService {

    private final Logger log = LoggerFactory.getLogger(DiluenteService.class);

    private final DiluenteRepository diluenteRepository;

    private final DiluenteMapper diluenteMapper;

    private final DiluenteSearchRepository diluenteSearchRepository;

    public DiluenteService(DiluenteRepository diluenteRepository, DiluenteMapper diluenteMapper, DiluenteSearchRepository diluenteSearchRepository) {
        this.diluenteRepository = diluenteRepository;
        this.diluenteMapper = diluenteMapper;
        this.diluenteSearchRepository = diluenteSearchRepository;
    }

    /**
     * Save a diluente.
     *
     * @param diluenteDTO the entity to save.
     * @return the persisted entity.
     */
    public DiluenteDTO save(DiluenteDTO diluenteDTO) {
        log.debug("Request to save Diluente : {}", diluenteDTO);
        Diluente diluente = diluenteMapper.toEntity(diluenteDTO);
        diluente = diluenteRepository.save(diluente);
        DiluenteDTO result = diluenteMapper.toDto(diluente);
        diluenteSearchRepository.save(diluente);
        return result;
    }

    /**
     * Get all the diluentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DiluenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Diluentes");
        return diluenteRepository.findAll(pageable)
            .map(diluenteMapper::toDto);
    }


    /**
     * Get one diluente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DiluenteDTO> findOne(Long id) {
        log.debug("Request to get Diluente : {}", id);
        return diluenteRepository.findById(id)
            .map(diluenteMapper::toDto);
    }

    /**
     * Delete the diluente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Diluente : {}", id);
        diluenteRepository.deleteById(id);
        diluenteSearchRepository.deleteById(id);
    }

    /**
     * Search for the diluente corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DiluenteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Diluentes for query {}", query);
        return diluenteSearchRepository.search(queryStringQuery(query), pageable)
            .map(diluenteMapper::toDto);
    }
}
