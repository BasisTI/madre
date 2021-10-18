package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.ProcessoAssincronoGrade;
import br.com.basis.madre.madreexames.repository.ProcessoAssincronoGradeRepository;
import br.com.basis.madre.madreexames.repository.search.ProcessoAssincronoGradeSearchRepository;
import br.com.basis.madre.madreexames.service.dto.ProcessoAssincronoGradeDTO;
import br.com.basis.madre.madreexames.service.mapper.ProcessoAssincronoGradeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ProcessoAssincronoGrade}.
 */
@Service
@Transactional
public class ProcessoAssincronoGradeService {

    private final Logger log = LoggerFactory.getLogger(ProcessoAssincronoGradeService.class);

    private final ProcessoAssincronoGradeRepository processoAssincronoGradeRepository;

    private final ProcessoAssincronoGradeMapper processoAssincronoGradeMapper;

    private final ProcessoAssincronoGradeSearchRepository processoAssincronoGradeSearchRepository;

    public ProcessoAssincronoGradeService(ProcessoAssincronoGradeRepository processoAssincronoGradeRepository, ProcessoAssincronoGradeMapper processoAssincronoGradeMapper, ProcessoAssincronoGradeSearchRepository processoAssincronoGradeSearchRepository) {
        this.processoAssincronoGradeRepository = processoAssincronoGradeRepository;
        this.processoAssincronoGradeMapper = processoAssincronoGradeMapper;
        this.processoAssincronoGradeSearchRepository = processoAssincronoGradeSearchRepository;
    }

    /**
     * Save a processoAssincronoGrade.
     *
     * @param processoAssincronoGradeDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessoAssincronoGradeDTO save(ProcessoAssincronoGradeDTO processoAssincronoGradeDTO) {
        log.debug("Request to save ProcessoAssincronoGrade : {}", processoAssincronoGradeDTO);
        ProcessoAssincronoGrade processoAssincronoGrade = processoAssincronoGradeMapper.toEntity(processoAssincronoGradeDTO);
        processoAssincronoGrade = processoAssincronoGradeRepository.save(processoAssincronoGrade);
        ProcessoAssincronoGradeDTO result = processoAssincronoGradeMapper.toDto(processoAssincronoGrade);
        processoAssincronoGradeSearchRepository.save(processoAssincronoGrade);
        return result;
    }

    /**
     * Get all the processoAssincronoGrades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessoAssincronoGradeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessoAssincronoGrades");
        return processoAssincronoGradeRepository.findAll(pageable)
            .map(processoAssincronoGradeMapper::toDto);
    }


    /**
     * Get one processoAssincronoGrade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessoAssincronoGradeDTO> findOne(String id) {
        log.debug("Request to get ProcessoAssincronoGrade : {}", id);
        return processoAssincronoGradeRepository.findById(id)
            .map(processoAssincronoGradeMapper::toDto);
    }

    /**
     * Delete the processoAssincronoGrade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ProcessoAssincronoGrade : {}", id);
        processoAssincronoGradeRepository.deleteById(id);
        processoAssincronoGradeSearchRepository.deleteById(id);
    }

    /**
     * Search for the processoAssincronoGrade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessoAssincronoGradeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProcessoAssincronoGrades for query {}", query);
        return processoAssincronoGradeSearchRepository.search(queryStringQuery(query), pageable)
            .map(processoAssincronoGradeMapper::toDto);
    }
}
