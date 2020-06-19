package br.com.basis.consulta.service;

import br.com.basis.consulta.domain.CondicaoDeAtendimento;
import br.com.basis.consulta.repository.CondicaoDeAtendimentoRepository;
import br.com.basis.consulta.repository.search.CondicaoDeAtendimentoSearchRepository;
import br.com.basis.consulta.service.dto.CondicaoDeAtendimentoDTO;
import br.com.basis.consulta.service.mapper.CondicaoDeAtendimentoMapper;
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
 * Service Implementation for managing {@link CondicaoDeAtendimento}.
 */
@Service
@Transactional
public class CondicaoDeAtendimentoService {

    private final Logger log = LoggerFactory.getLogger(CondicaoDeAtendimentoService.class);

    private final CondicaoDeAtendimentoRepository condicaoDeAtendimentoRepository;

    private final CondicaoDeAtendimentoMapper condicaoDeAtendimentoMapper;

    private final CondicaoDeAtendimentoSearchRepository condicaoDeAtendimentoSearchRepository;

    public CondicaoDeAtendimentoService(CondicaoDeAtendimentoRepository condicaoDeAtendimentoRepository, CondicaoDeAtendimentoMapper condicaoDeAtendimentoMapper, CondicaoDeAtendimentoSearchRepository condicaoDeAtendimentoSearchRepository) {
        this.condicaoDeAtendimentoRepository = condicaoDeAtendimentoRepository;
        this.condicaoDeAtendimentoMapper = condicaoDeAtendimentoMapper;
        this.condicaoDeAtendimentoSearchRepository = condicaoDeAtendimentoSearchRepository;
    }

    /**
     * Save a condicaoDeAtendimento.
     *
     * @param condicaoDeAtendimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public CondicaoDeAtendimentoDTO save(CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO) {
        log.debug("Request to save CondicaoDeAtendimento : {}", condicaoDeAtendimentoDTO);
        CondicaoDeAtendimento condicaoDeAtendimento = condicaoDeAtendimentoMapper.toEntity(condicaoDeAtendimentoDTO);
        condicaoDeAtendimento = condicaoDeAtendimentoRepository.save(condicaoDeAtendimento);
        CondicaoDeAtendimentoDTO result = condicaoDeAtendimentoMapper.toDto(condicaoDeAtendimento);
        condicaoDeAtendimentoSearchRepository.save(condicaoDeAtendimento);
        return result;
    }

    /**
     * Get all the condicaoDeAtendimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CondicaoDeAtendimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CondicaoDeAtendimentos");
        return condicaoDeAtendimentoRepository.findAll(pageable)
            .map(condicaoDeAtendimentoMapper::toDto);
    }



    /**
    *  Get all the condicaoDeAtendimentos where Emergencia is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CondicaoDeAtendimentoDTO> findAllWhereEmergenciaIsNull() {
        log.debug("Request to get all condicaoDeAtendimentos where Emergencia is null");
        return StreamSupport
            .stream(condicaoDeAtendimentoRepository.findAll().spliterator(), false)
            .filter(condicaoDeAtendimento -> condicaoDeAtendimento.getEmergencia() == null)
            .map(condicaoDeAtendimentoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one condicaoDeAtendimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CondicaoDeAtendimentoDTO> findOne(Long id) {
        log.debug("Request to get CondicaoDeAtendimento : {}", id);
        return condicaoDeAtendimentoRepository.findById(id)
            .map(condicaoDeAtendimentoMapper::toDto);
    }

    /**
     * Delete the condicaoDeAtendimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CondicaoDeAtendimento : {}", id);
        condicaoDeAtendimentoRepository.deleteById(id);
        condicaoDeAtendimentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the condicaoDeAtendimento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CondicaoDeAtendimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CondicaoDeAtendimentos for query {}", query);
        return condicaoDeAtendimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(condicaoDeAtendimentoMapper::toDto);
    }
}
