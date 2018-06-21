package br.com.basis.madre.pacientes.service.impl;

import br.com.basis.madre.pacientes.service.PacienteService;
import br.com.basis.madre.pacientes.domain.Paciente;
import br.com.basis.madre.pacientes.repository.PacienteRepository;
import br.com.basis.madre.pacientes.repository.search.PacienteSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Paciente.
 */
@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final Logger log = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private final PacienteRepository pacienteRepository;

    private final PacienteSearchRepository pacienteSearchRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, PacienteSearchRepository pacienteSearchRepository) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteSearchRepository = pacienteSearchRepository;
    }

    /**
     * Save a paciente.
     *
     * @param paciente the entity to save
     * @return the persisted entity
     */
    @Override
    public Paciente save(Paciente paciente) {
        log.debug("Request to save Paciente : {}", paciente);
        Paciente result = pacienteRepository.save(paciente);
        pacienteSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the pacientes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findAll(Pageable pageable) {
        log.debug("Request to get all Pacientes");
        return pacienteRepository.findAll(pageable);
    }

    /**
     * Get one paciente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Paciente findOne(Long id) {
        log.debug("Request to get Paciente : {}", id);
        return pacienteRepository.findOne(id);
    }

    /**
     * Delete the paciente by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paciente : {}", id);
        pacienteRepository.delete(id);
        pacienteSearchRepository.delete(id);
    }

    /**
     * Search for the paciente corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pacientes for query {}", query);
        Page<Paciente> result = pacienteSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
