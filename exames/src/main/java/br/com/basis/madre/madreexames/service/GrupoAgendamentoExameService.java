package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.GrupoAgendamentoExame;
import br.com.basis.madre.madreexames.repository.GrupoAgendamentoExameRepository;
import br.com.basis.madre.madreexames.repository.search.GrupoAgendamentoExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.GrupoAgendamentoExameDTO;
import br.com.basis.madre.madreexames.service.mapper.GrupoAgendamentoExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link GrupoAgendamentoExame}.
 */
@Service
@Transactional
public class GrupoAgendamentoExameService {

    private final Logger log = LoggerFactory.getLogger(GrupoAgendamentoExameService.class);

    private final GrupoAgendamentoExameRepository grupoAgendamentoExameRepository;

    private final GrupoAgendamentoExameMapper grupoAgendamentoExameMapper;

    private final GrupoAgendamentoExameSearchRepository grupoAgendamentoExameSearchRepository;

    public GrupoAgendamentoExameService(GrupoAgendamentoExameRepository grupoAgendamentoExameRepository, GrupoAgendamentoExameMapper grupoAgendamentoExameMapper, GrupoAgendamentoExameSearchRepository grupoAgendamentoExameSearchRepository) {
        this.grupoAgendamentoExameRepository = grupoAgendamentoExameRepository;
        this.grupoAgendamentoExameMapper = grupoAgendamentoExameMapper;
        this.grupoAgendamentoExameSearchRepository = grupoAgendamentoExameSearchRepository;
    }

    /**
     * Save a grupoAgendamentoExame.
     *
     * @param grupoAgendamentoExameDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoAgendamentoExameDTO save(GrupoAgendamentoExameDTO grupoAgendamentoExameDTO) {
        log.debug("Request to save GrupoAgendamentoExame : {}", grupoAgendamentoExameDTO);
        GrupoAgendamentoExame grupoAgendamentoExame = grupoAgendamentoExameMapper.toEntity(grupoAgendamentoExameDTO);
        grupoAgendamentoExame = grupoAgendamentoExameRepository.save(grupoAgendamentoExame);
        GrupoAgendamentoExameDTO result = grupoAgendamentoExameMapper.toDto(grupoAgendamentoExame);
        grupoAgendamentoExameSearchRepository.save(grupoAgendamentoExame);
        return result;
    }

    /**
     * Get all the grupoAgendamentoExames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupoAgendamentoExameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GrupoAgendamentoExames");
        return grupoAgendamentoExameRepository.findAll(pageable)
            .map(grupoAgendamentoExameMapper::toDto);
    }


    /**
     * Get all the grupoAgendamentoExames with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GrupoAgendamentoExameDTO> findAllWithEagerRelationships(Pageable pageable) {
        return grupoAgendamentoExameRepository.findAllWithEagerRelationships(pageable).map(grupoAgendamentoExameMapper::toDto);
    }

    /**
     * Get one grupoAgendamentoExame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoAgendamentoExameDTO> findOne(Long id) {
        log.debug("Request to get GrupoAgendamentoExame : {}", id);
        return grupoAgendamentoExameRepository.findOneWithEagerRelationships(id)
            .map(grupoAgendamentoExameMapper::toDto);
    }

    /**
     * Delete the grupoAgendamentoExame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoAgendamentoExame : {}", id);
        grupoAgendamentoExameRepository.deleteById(id);
        grupoAgendamentoExameSearchRepository.deleteById(id);
    }

    /**
     * Search for the grupoAgendamentoExame corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupoAgendamentoExameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GrupoAgendamentoExames for query {}", query);
        return grupoAgendamentoExameSearchRepository.search(queryStringQuery(query), pageable)
            .map(grupoAgendamentoExameMapper::toDto);
    }
}
