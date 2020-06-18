package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.UnidadeMedida;
import br.com.basis.suprimentos.repository.UnidadeMedidaRepository;
import br.com.basis.suprimentos.repository.search.UnidadeMedidaSearchRepository;
import br.com.basis.suprimentos.service.dto.UnidadeMedidaDTO;
import br.com.basis.suprimentos.service.mapper.UnidadeMedidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UnidadeMedida}.
 */
@Service
@Transactional
public class UnidadeMedidaService {

    private final Logger log = LoggerFactory.getLogger(UnidadeMedidaService.class);

    private final UnidadeMedidaRepository unidadeMedidaRepository;

    private final UnidadeMedidaMapper unidadeMedidaMapper;

    private final UnidadeMedidaSearchRepository unidadeMedidaSearchRepository;

    public UnidadeMedidaService(UnidadeMedidaRepository unidadeMedidaRepository, UnidadeMedidaMapper unidadeMedidaMapper, UnidadeMedidaSearchRepository unidadeMedidaSearchRepository) {
        this.unidadeMedidaRepository = unidadeMedidaRepository;
        this.unidadeMedidaMapper = unidadeMedidaMapper;
        this.unidadeMedidaSearchRepository = unidadeMedidaSearchRepository;
    }

    /**
     * Save a unidadeMedida.
     *
     * @param unidadeMedidaDTO the entity to save.
     * @return the persisted entity.
     */
    public UnidadeMedidaDTO save(UnidadeMedidaDTO unidadeMedidaDTO) {
        log.debug("Request to save UnidadeMedida : {}", unidadeMedidaDTO);
        UnidadeMedida unidadeMedida = unidadeMedidaMapper.toEntity(unidadeMedidaDTO);
        unidadeMedida = unidadeMedidaRepository.save(unidadeMedida);
        UnidadeMedidaDTO result = unidadeMedidaMapper.toDto(unidadeMedida);
        unidadeMedidaSearchRepository.save(unidadeMedida);
        return result;
    }

    /**
     * Get all the unidadeMedidas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeMedidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadeMedidas");
        return unidadeMedidaRepository.findAll(pageable)
            .map(unidadeMedidaMapper::toDto);
    }


    /**
     * Get one unidadeMedida by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnidadeMedidaDTO> findOne(Long id) {
        log.debug("Request to get UnidadeMedida : {}", id);
        return unidadeMedidaRepository.findById(id)
            .map(unidadeMedidaMapper::toDto);
    }

    /**
     * Delete the unidadeMedida by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UnidadeMedida : {}", id);
        unidadeMedidaRepository.deleteById(id);
        unidadeMedidaSearchRepository.deleteById(id);
    }

    /**
     * Search for the unidadeMedida corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeMedidaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnidadeMedidas for query {}", query);
        return unidadeMedidaSearchRepository.search(queryStringQuery(query), pageable)
            .map(unidadeMedidaMapper::toDto);
    }
}
