package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.TipoUnidadeDieta;
import br.com.basis.madre.prescricao.repository.TipoUnidadeDietaRepository;
import br.com.basis.madre.prescricao.repository.search.TipoUnidadeDietaSearchRepository;
import br.com.basis.madre.prescricao.service.dto.TipoUnidadeDietaDTO;
import br.com.basis.madre.prescricao.service.mapper.TipoUnidadeDietaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoUnidadeDieta}.
 */
@Service
@Transactional
public class TipoUnidadeDietaService {

    private final Logger log = LoggerFactory.getLogger(TipoUnidadeDietaService.class);

    private final TipoUnidadeDietaRepository tipoUnidadeDietaRepository;

    private final TipoUnidadeDietaMapper tipoUnidadeDietaMapper;

    private final TipoUnidadeDietaSearchRepository tipoUnidadeDietaSearchRepository;

    public TipoUnidadeDietaService(TipoUnidadeDietaRepository tipoUnidadeDietaRepository, TipoUnidadeDietaMapper tipoUnidadeDietaMapper, TipoUnidadeDietaSearchRepository tipoUnidadeDietaSearchRepository) {
        this.tipoUnidadeDietaRepository = tipoUnidadeDietaRepository;
        this.tipoUnidadeDietaMapper = tipoUnidadeDietaMapper;
        this.tipoUnidadeDietaSearchRepository = tipoUnidadeDietaSearchRepository;
    }

    /**
     * Save a tipoUnidadeDieta.
     *
     * @param tipoUnidadeDietaDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoUnidadeDietaDTO save(TipoUnidadeDietaDTO tipoUnidadeDietaDTO) {
        log.debug("Request to save TipoUnidadeDieta : {}", tipoUnidadeDietaDTO);
        TipoUnidadeDieta tipoUnidadeDieta = tipoUnidadeDietaMapper.toEntity(tipoUnidadeDietaDTO);
        tipoUnidadeDieta = tipoUnidadeDietaRepository.save(tipoUnidadeDieta);
        TipoUnidadeDietaDTO result = tipoUnidadeDietaMapper.toDto(tipoUnidadeDieta);
        tipoUnidadeDietaSearchRepository.save(tipoUnidadeDieta);
        return result;
    }

    /**
     * Get all the tipoUnidadeDietas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoUnidadeDietaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoUnidadeDietas");
        return tipoUnidadeDietaRepository.findAll(pageable)
            .map(tipoUnidadeDietaMapper::toDto);
    }


    /**
     * Get one tipoUnidadeDieta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoUnidadeDietaDTO> findOne(Long id) {
        log.debug("Request to get TipoUnidadeDieta : {}", id);
        return tipoUnidadeDietaRepository.findById(id)
            .map(tipoUnidadeDietaMapper::toDto);
    }

    /**
     * Delete the tipoUnidadeDieta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoUnidadeDieta : {}", id);
        tipoUnidadeDietaRepository.deleteById(id);
        tipoUnidadeDietaSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoUnidadeDieta corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoUnidadeDietaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoUnidadeDietas for query {}", query);
        return tipoUnidadeDietaSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoUnidadeDietaMapper::toDto);
    }
}
