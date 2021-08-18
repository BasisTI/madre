package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.GradeDeAgendamento;
import br.com.basis.madre.madreexames.repository.GradeDeAgendamentoRepository;
import br.com.basis.madre.madreexames.repository.search.GradeDeAgendamentoSearchRepository;
import br.com.basis.madre.madreexames.service.dto.GradeDeAgendamentoDTO;
import br.com.basis.madre.madreexames.service.mapper.GradeDeAgendamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link GradeDeAgendamento}.
 */
@Service
@Transactional
public class GradeDeAgendamentoService {

    private final Logger log = LoggerFactory.getLogger(GradeDeAgendamentoService.class);

    private final GradeDeAgendamentoRepository gradeDeAgendamentoRepository;

    private final GradeDeAgendamentoMapper gradeDeAgendamentoMapper;

    private final GradeDeAgendamentoSearchRepository gradeDeAgendamentoSearchRepository;

    public GradeDeAgendamentoService(GradeDeAgendamentoRepository gradeDeAgendamentoRepository, GradeDeAgendamentoMapper gradeDeAgendamentoMapper, GradeDeAgendamentoSearchRepository gradeDeAgendamentoSearchRepository) {
        this.gradeDeAgendamentoRepository = gradeDeAgendamentoRepository;
        this.gradeDeAgendamentoMapper = gradeDeAgendamentoMapper;
        this.gradeDeAgendamentoSearchRepository = gradeDeAgendamentoSearchRepository;
    }

    /**
     * Save a gradeDeAgendamento.
     *
     * @param gradeDeAgendamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public GradeDeAgendamentoDTO save(GradeDeAgendamentoDTO gradeDeAgendamentoDTO) {
        log.debug("Request to save GradeDeAgendamento : {}", gradeDeAgendamentoDTO);
        GradeDeAgendamento gradeDeAgendamento = gradeDeAgendamentoMapper.toEntity(gradeDeAgendamentoDTO);
        gradeDeAgendamento = gradeDeAgendamentoRepository.save(gradeDeAgendamento);
        GradeDeAgendamentoDTO result = gradeDeAgendamentoMapper.toDto(gradeDeAgendamento);
        gradeDeAgendamentoSearchRepository.save(gradeDeAgendamento);
        return result;
    }

    /**
     * Get all the gradeDeAgendamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GradeDeAgendamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GradeDeAgendamentos");
        return gradeDeAgendamentoRepository.findAll(pageable)
            .map(gradeDeAgendamentoMapper::toDto);
    }


    /**
     * Get one gradeDeAgendamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GradeDeAgendamentoDTO> findOne(Long id) {
        log.debug("Request to get GradeDeAgendamento : {}", id);
        return gradeDeAgendamentoRepository.findById(id)
            .map(gradeDeAgendamentoMapper::toDto);
    }

    /**
     * Delete the gradeDeAgendamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GradeDeAgendamento : {}", id);
        gradeDeAgendamentoRepository.deleteById(id);
        gradeDeAgendamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the gradeDeAgendamento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GradeDeAgendamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GradeDeAgendamentos for query {}", query);
        return gradeDeAgendamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(gradeDeAgendamentoMapper::toDto);
    }
}
