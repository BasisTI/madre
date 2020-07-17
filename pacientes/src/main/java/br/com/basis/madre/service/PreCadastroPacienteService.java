package br.com.basis.madre.service;

import br.com.basis.madre.domain.PreCadastroPaciente;
import br.com.basis.madre.repository.PreCadastroPacienteRepository;
import br.com.basis.madre.repository.search.PreCadastroPacienteSearchRepository;
import br.com.basis.madre.service.dto.PreCadastroPacienteDTO;
import br.com.basis.madre.service.mapper.PreCadastroPacienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link PreCadastroPaciente}.
 */
@Service
@Transactional
public class PreCadastroPacienteService {

    private final Logger log = LoggerFactory.getLogger(PreCadastroPacienteService.class);

    private final PreCadastroPacienteRepository preCadastroPacienteRepository;

    private final PreCadastroPacienteMapper preCadastroPacienteMapper;

    private final PreCadastroPacienteSearchRepository preCadastroPacienteSearchRepository;

    public PreCadastroPacienteService(PreCadastroPacienteRepository preCadastroPacienteRepository, PreCadastroPacienteMapper preCadastroPacienteMapper, PreCadastroPacienteSearchRepository preCadastroPacienteSearchRepository) {
        this.preCadastroPacienteRepository = preCadastroPacienteRepository;
        this.preCadastroPacienteMapper = preCadastroPacienteMapper;
        this.preCadastroPacienteSearchRepository = preCadastroPacienteSearchRepository;
    }

    /**
     * Save a preCadastroPaciente.
     *
     * @param preCadastroPacienteDTO the entity to save.
     * @return the persisted entity.
     */
    public PreCadastroPacienteDTO save(PreCadastroPacienteDTO preCadastroPacienteDTO) {
        log.debug("Request to save PreCadastroPaciente : {}", preCadastroPacienteDTO);
        PreCadastroPaciente preCadastroPaciente = preCadastroPacienteMapper.toEntity(preCadastroPacienteDTO);
        preCadastroPaciente = preCadastroPacienteRepository.save(preCadastroPaciente);
        PreCadastroPacienteDTO result = preCadastroPacienteMapper.toDto(preCadastroPaciente);
        preCadastroPacienteSearchRepository.save(preCadastroPaciente);
        return result;
    }

    /**
     * Get all the preCadastroPacientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PreCadastroPacienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PreCadastroPacientes");
        return preCadastroPacienteRepository.findAll(pageable)
            .map(preCadastroPacienteMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PreCadastroPacienteDTO> findByName(String nome,Pageable pageable) {
        log.debug("Request to get all PreCadastroPacientes");
        return preCadastroPacienteRepository.findByNomeContainsIgnoreCase(nome, pageable)
            .map(preCadastroPacienteMapper::toDto);
    }


    /**
     * Get one preCadastroPaciente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PreCadastroPacienteDTO> findOne(Long id) {
        log.debug("Request to get PreCadastroPaciente : {}", id);
        return preCadastroPacienteRepository.findById(id)
            .map(preCadastroPacienteMapper::toDto);
    }

    /**
     * Delete the preCadastroPaciente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PreCadastroPaciente : {}", id);
        preCadastroPacienteRepository.deleteById(id);
        preCadastroPacienteSearchRepository.deleteById(id);
    }

    /**
     * Search for the preCadastroPaciente corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PreCadastroPacienteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PreCadastroPacientes for query {}", query);
        return preCadastroPacienteSearchRepository.search(queryStringQuery(query), pageable)
            .map(preCadastroPacienteMapper::toDto);
    }
}
