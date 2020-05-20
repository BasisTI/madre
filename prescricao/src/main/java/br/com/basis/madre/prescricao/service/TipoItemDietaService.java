package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.TipoItemDieta;
import br.com.basis.madre.prescricao.repository.TipoItemDietaRepository;
import br.com.basis.madre.prescricao.repository.search.TipoItemDietaSearchRepository;
import br.com.basis.madre.prescricao.service.dto.TipoItemDietaDTO;
import br.com.basis.madre.prescricao.service.mapper.TipoItemDietaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoItemDieta}.
 */
@Service
@Transactional
public class TipoItemDietaService {

    private final Logger log = LoggerFactory.getLogger(TipoItemDietaService.class);

    private final TipoItemDietaRepository tipoItemDietaRepository;

    private final TipoItemDietaMapper tipoItemDietaMapper;

    private final TipoItemDietaSearchRepository tipoItemDietaSearchRepository;

    public TipoItemDietaService(TipoItemDietaRepository tipoItemDietaRepository, TipoItemDietaMapper tipoItemDietaMapper, TipoItemDietaSearchRepository tipoItemDietaSearchRepository) {
        this.tipoItemDietaRepository = tipoItemDietaRepository;
        this.tipoItemDietaMapper = tipoItemDietaMapper;
        this.tipoItemDietaSearchRepository = tipoItemDietaSearchRepository;
    }

    /**
     * Save a tipoItemDieta.
     *
     * @param tipoItemDietaDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoItemDietaDTO save(TipoItemDietaDTO tipoItemDietaDTO) {
        log.debug("Request to save TipoItemDieta : {}", tipoItemDietaDTO);
        TipoItemDieta tipoItemDieta = tipoItemDietaMapper.toEntity(tipoItemDietaDTO);
        tipoItemDieta = tipoItemDietaRepository.save(tipoItemDieta);
        TipoItemDietaDTO result = tipoItemDietaMapper.toDto(tipoItemDieta);
        tipoItemDietaSearchRepository.save(tipoItemDieta);
        return result;
    }

    /**
     * Get all the tipoItemDietas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoItemDietaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoItemDietas");
        return tipoItemDietaRepository.findAll(pageable)
            .map(tipoItemDietaMapper::toDto);
    }


    /**
     * Get one tipoItemDieta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoItemDietaDTO> findOne(Long id) {
        log.debug("Request to get TipoItemDieta : {}", id);
        return tipoItemDietaRepository.findById(id)
            .map(tipoItemDietaMapper::toDto);
    }

    /**
     * Delete the tipoItemDieta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoItemDieta : {}", id);
        tipoItemDietaRepository.deleteById(id);
        tipoItemDietaSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoItemDieta corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoItemDietaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoItemDietas for query {}", query);
        return tipoItemDietaSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoItemDietaMapper::toDto);
    }
}
