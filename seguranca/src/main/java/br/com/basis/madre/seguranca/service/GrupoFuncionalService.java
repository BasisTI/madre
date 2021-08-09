package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.GrupoFuncional;
import br.com.basis.madre.seguranca.repository.GrupoFuncionalRepository;
import br.com.basis.madre.seguranca.repository.search.GrupoFuncionalSearchRepository;
import br.com.basis.madre.seguranca.service.dto.GrupoFuncionalDTO;
import br.com.basis.madre.seguranca.service.mapper.GrupoFuncionalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link GrupoFuncional}.
 */
@Service
@Transactional
public class GrupoFuncionalService {

    private final Logger log = LoggerFactory.getLogger(GrupoFuncionalService.class);

    private final GrupoFuncionalRepository grupoFuncionalRepository;

    private final GrupoFuncionalMapper grupoFuncionalMapper;

    private final GrupoFuncionalSearchRepository grupoFuncionalSearchRepository;

    public GrupoFuncionalService(GrupoFuncionalRepository grupoFuncionalRepository, GrupoFuncionalMapper grupoFuncionalMapper, GrupoFuncionalSearchRepository grupoFuncionalSearchRepository) {
        this.grupoFuncionalRepository = grupoFuncionalRepository;
        this.grupoFuncionalMapper = grupoFuncionalMapper;
        this.grupoFuncionalSearchRepository = grupoFuncionalSearchRepository;
    }

    /**
     * Save a grupoFuncional.
     *
     * @param grupoFuncionalDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoFuncionalDTO save(GrupoFuncionalDTO grupoFuncionalDTO) {
        log.debug("Request to save GrupoFuncional : {}", grupoFuncionalDTO);
        GrupoFuncional grupoFuncional = grupoFuncionalMapper.toEntity(grupoFuncionalDTO);
        grupoFuncional = grupoFuncionalRepository.save(grupoFuncional);
        GrupoFuncionalDTO result = grupoFuncionalMapper.toDto(grupoFuncional);
        grupoFuncionalSearchRepository.save(grupoFuncional);
        return result;
    }

    /**
     * Get all the grupoFuncionals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupoFuncionalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GrupoFuncionals");
        return grupoFuncionalRepository.findAll(pageable)
            .map(grupoFuncionalMapper::toDto);
    }


    /**
     * Get one grupoFuncional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoFuncionalDTO> findOne(Long id) {
        log.debug("Request to get GrupoFuncional : {}", id);
        return grupoFuncionalRepository.findById(id)
            .map(grupoFuncionalMapper::toDto);
    }

    /**
     * Delete the grupoFuncional by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoFuncional : {}", id);
        grupoFuncionalRepository.deleteById(id);
        grupoFuncionalSearchRepository.deleteById(id);
    }

    /**
     * Search for the grupoFuncional corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupoFuncionalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GrupoFuncionals for query {}", query);
        return grupoFuncionalSearchRepository.search(queryStringQuery(query), pageable)
            .map(grupoFuncionalMapper::toDto);
    }
}
