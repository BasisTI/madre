package br.com.basis.consulta.service;

import br.com.basis.consulta.domain.FormaDeAgendamento;
import br.com.basis.consulta.repository.FormaDeAgendamentoRepository;
import br.com.basis.consulta.repository.search.FormaDeAgendamentoSearchRepository;
import br.com.basis.consulta.service.dto.FormaDeAgendamentoDTO;
import br.com.basis.consulta.service.mapper.FormaDeAgendamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FormaDeAgendamento}.
 */
@Service
@Transactional
public class FormaDeAgendamentoService {

    private final Logger log = LoggerFactory.getLogger(FormaDeAgendamentoService.class);

    private final FormaDeAgendamentoRepository formaDeAgendamentoRepository;

    private final FormaDeAgendamentoMapper formaDeAgendamentoMapper;

    private final FormaDeAgendamentoSearchRepository formaDeAgendamentoSearchRepository;

    public FormaDeAgendamentoService(FormaDeAgendamentoRepository formaDeAgendamentoRepository, FormaDeAgendamentoMapper formaDeAgendamentoMapper, FormaDeAgendamentoSearchRepository formaDeAgendamentoSearchRepository) {
        this.formaDeAgendamentoRepository = formaDeAgendamentoRepository;
        this.formaDeAgendamentoMapper = formaDeAgendamentoMapper;
        this.formaDeAgendamentoSearchRepository = formaDeAgendamentoSearchRepository;
    }

    /**
     * Save a formaDeAgendamento.
     *
     * @param formaDeAgendamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public FormaDeAgendamentoDTO save(FormaDeAgendamentoDTO formaDeAgendamentoDTO) {
        log.debug("Request to save FormaDeAgendamento : {}", formaDeAgendamentoDTO);
        FormaDeAgendamento formaDeAgendamento = formaDeAgendamentoMapper.toEntity(formaDeAgendamentoDTO);
        formaDeAgendamento = formaDeAgendamentoRepository.save(formaDeAgendamento);
        FormaDeAgendamentoDTO result = formaDeAgendamentoMapper.toDto(formaDeAgendamento);
        formaDeAgendamentoSearchRepository.save(formaDeAgendamento);
        return result;
    }

    /**
     * Get all the formaDeAgendamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FormaDeAgendamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FormaDeAgendamentos");
        return formaDeAgendamentoRepository.findAll(pageable)
            .map(formaDeAgendamentoMapper::toDto);
    }



    /**
    *  Get all the formaDeAgendamentos where Emergencia is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<FormaDeAgendamentoDTO> findAllWhereEmergenciaIsNull() {
        log.debug("Request to get all formaDeAgendamentos where Emergencia is null");
        return StreamSupport
            .stream(formaDeAgendamentoRepository.findAll().spliterator(), false)
            .filter(formaDeAgendamento -> formaDeAgendamento.getEmergencia() == null)
            .map(formaDeAgendamentoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one formaDeAgendamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FormaDeAgendamentoDTO> findOne(Long id) {
        log.debug("Request to get FormaDeAgendamento : {}", id);
        return formaDeAgendamentoRepository.findById(id)
            .map(formaDeAgendamentoMapper::toDto);
    }

    /**
     * Delete the formaDeAgendamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FormaDeAgendamento : {}", id);
        formaDeAgendamentoRepository.deleteById(id);
        formaDeAgendamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the formaDeAgendamento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FormaDeAgendamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FormaDeAgendamentos for query {}", query);
        return formaDeAgendamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(formaDeAgendamentoMapper::toDto);
    }
}
