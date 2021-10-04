package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;
import br.com.basis.madre.madreexames.repository.GradeAgendamentoExameRepository;
import br.com.basis.madre.madreexames.repository.search.GradeAgendamentoExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.GradeAgendamentoExameDTO;
import br.com.basis.madre.madreexames.service.mapper.GradeAgendamentoExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link GradeAgendamentoExame}.
 */
@Service
@Transactional
public class GradeAgendamentoExameService {

    private final Logger log = LoggerFactory.getLogger(GradeAgendamentoExameService.class);

    private final GradeAgendamentoExameRepository gradeAgendamentoExameRepository;

    private final GradeAgendamentoExameMapper gradeAgendamentoExameMapper;

    private final GradeAgendamentoExameSearchRepository gradeAgendamentoExameSearchRepository;

    public GradeAgendamentoExameService(GradeAgendamentoExameRepository gradeAgendamentoExameRepository, GradeAgendamentoExameMapper gradeAgendamentoExameMapper, GradeAgendamentoExameSearchRepository gradeAgendamentoExameSearchRepository) {
        this.gradeAgendamentoExameRepository = gradeAgendamentoExameRepository;
        this.gradeAgendamentoExameMapper = gradeAgendamentoExameMapper;
        this.gradeAgendamentoExameSearchRepository = gradeAgendamentoExameSearchRepository;
    }

    /**
     * Save a gradeAgendamentoExame.
     *
     * @param gradeAgendamentoExameDTO the entity to save.
     * @return the persisted entity.
     */
    public GradeAgendamentoExameDTO save(GradeAgendamentoExameDTO gradeAgendamentoExameDTO) {
        log.debug("Request to save GradeAgendamentoExame : {}", gradeAgendamentoExameDTO);
        calcularDuracaoDeHorarios(gradeAgendamentoExameDTO);
        GradeAgendamentoExame gradeAgendamentoExame = gradeAgendamentoExameMapper.toEntity(gradeAgendamentoExameDTO);
        gradeAgendamentoExame = gradeAgendamentoExameRepository.save(gradeAgendamentoExame);
        GradeAgendamentoExameDTO result = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);
        gradeAgendamentoExameSearchRepository.save(gradeAgendamentoExame);
        return result;
    }

    public void calcularDuracaoDeHorarios(GradeAgendamentoExameDTO gradeAgendamentoExameDTO) {
        Duration intervaloInicioFim = Duration.between(gradeAgendamentoExameDTO.getHoraInicio(),
            gradeAgendamentoExameDTO.getHoraFim());
        gradeAgendamentoExameDTO.setDuracao(intervaloInicioFim.dividedBy(gradeAgendamentoExameDTO
            .getNumeroDeHorarios()));
    }

    /**
     * Get all the gradeAgendamentoExames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GradeAgendamentoExameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GradeAgendamentoExames");
        return gradeAgendamentoExameRepository.findAll(pageable)
            .map(gradeAgendamentoExameMapper::toDto);
    }


    /**
     * Get all the gradeAgendamentoExames with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GradeAgendamentoExameDTO> findAllWithEagerRelationships(Pageable pageable) {
        return gradeAgendamentoExameRepository.findAllWithEagerRelationships(pageable).map(gradeAgendamentoExameMapper::toDto);
    }

    /**
     * Get one gradeAgendamentoExame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GradeAgendamentoExameDTO> findOne(Long id) {
        log.debug("Request to get GradeAgendamentoExame : {}", id);
        return gradeAgendamentoExameRepository.findOneWithEagerRelationships(id)
            .map(gradeAgendamentoExameMapper::toDto);
    }

    /**
     * Delete the gradeAgendamentoExame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GradeAgendamentoExame : {}", id);
        gradeAgendamentoExameRepository.deleteById(id);
        gradeAgendamentoExameSearchRepository.deleteById(id);
    }

    /**
     * Search for the gradeAgendamentoExame corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GradeAgendamentoExameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GradeAgendamentoExames for query {}", query);
        return gradeAgendamentoExameSearchRepository.search(queryStringQuery(query), pageable)
            .map(gradeAgendamentoExameMapper::toDto);
    }
}
