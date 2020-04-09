package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.repository.PacienteRepository;
import br.com.basis.madre.repository.search.PacienteSearchRepository;
import br.com.basis.madre.service.dto.PacienteDTO;
import br.com.basis.madre.service.mapper.PacienteMapper;
import br.com.basis.madre.service.projection.PacienteSummary;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Paciente}.
 */
@Service
@Transactional
public class PacienteService {

    private final Logger log = LoggerFactory.getLogger(PacienteService.class);

    private final PacienteRepository pacienteRepository;

    private final PacienteMapper pacienteMapper;

    private final PacienteSearchRepository pacienteSearchRepository;

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper,
        PacienteSearchRepository pacienteSearchRepository) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
        this.pacienteSearchRepository = pacienteSearchRepository;
    }

    /**
     * Save a paciente.
     *
     * @param pacienteDTO the entity to save.
     * @return the persisted entity.
     */
    public PacienteDTO save(PacienteDTO pacienteDTO) {
        log.debug("Request to save Paciente : {}", pacienteDTO);
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        PacienteDTO result = pacienteMapper.toDto(paciente);
        pacienteSearchRepository.save(paciente);
        return result;
    }

    /**
     * Get all the pacientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PacienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pacientes");
        return pacienteRepository.findAll(pageable)
            .map(pacienteMapper::toDto);
    }

    /**
     * Get one paciente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PacienteDTO> findOne(Long id) {
        log.debug("Request to get Paciente : {}", id);
        return pacienteRepository.findById(id)
            .map(pacienteMapper::toDto);
    }

    /**
     * Delete the paciente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Paciente : {}", id);
        pacienteRepository.deleteById(id);
        pacienteSearchRepository.deleteById(id);
    }

    /**
     * Search for the paciente corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PacienteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pacientes for query {}", query);
        return pacienteSearchRepository.search(queryStringQuery(query), pageable)
            .map(pacienteMapper::toDto);
    }

    public Page<PacienteSummary> getPacienteSummary(Pageable pageable) {
        return pacienteRepository.getPacienteSummary(pageable);
    }
}
