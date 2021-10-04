package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Dia;
import br.com.basis.madre.madreexames.repository.DiaRepository;
import br.com.basis.madre.madreexames.repository.search.DiaSearchRepository;
import br.com.basis.madre.madreexames.service.dto.DiaDTO;
import br.com.basis.madre.madreexames.service.mapper.DiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Dia}.
 */
@Service
@Transactional
public class DiaService {

    private final Logger log = LoggerFactory.getLogger(DiaService.class);

    private final DiaRepository diaRepository;

    private final DiaMapper diaMapper;

    private final DiaSearchRepository diaSearchRepository;

    public DiaService(DiaRepository diaRepository, DiaMapper diaMapper, DiaSearchRepository diaSearchRepository) {
        this.diaRepository = diaRepository;
        this.diaMapper = diaMapper;
        this.diaSearchRepository = diaSearchRepository;
    }

    /**
     * Save a dia.
     *
     * @param diaDTO the entity to save.
     * @return the persisted entity.
     */
    public DiaDTO save(DiaDTO diaDTO) {
        log.debug("Request to save Dia : {}", diaDTO);
        Dia dia = diaMapper.toEntity(diaDTO);
        dia = diaRepository.save(dia);
        DiaDTO result = diaMapper.toDto(dia);
        diaSearchRepository.save(dia);
        return result;
    }

    /**
     * Get all the dias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dias");
        return diaRepository.findAll(pageable)
            .map(diaMapper::toDto);
    }


    /**
     * Get one dia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DiaDTO> findOne(Long id) {
        log.debug("Request to get Dia : {}", id);
        return diaRepository.findById(id)
            .map(diaMapper::toDto);
    }

    /**
     * Delete the dia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dia : {}", id);
        diaRepository.deleteById(id);
        diaSearchRepository.deleteById(id);
    }

    /**
     * Search for the dia corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DiaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dias for query {}", query);
        return diaSearchRepository.search(queryStringQuery(query), pageable)
            .map(diaMapper::toDto);
    }
}
