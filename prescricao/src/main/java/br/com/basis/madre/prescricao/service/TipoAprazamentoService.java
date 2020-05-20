package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.TipoAprazamento;
import br.com.basis.madre.prescricao.repository.TipoAprazamentoRepository;
import br.com.basis.madre.prescricao.repository.search.TipoAprazamentoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.TipoAprazamentoDTO;
import br.com.basis.madre.prescricao.service.mapper.TipoAprazamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoAprazamento}.
 */
@Service
@Transactional
public class TipoAprazamentoService {

    private final Logger log = LoggerFactory.getLogger(TipoAprazamentoService.class);

    private final TipoAprazamentoRepository tipoAprazamentoRepository;

    private final TipoAprazamentoMapper tipoAprazamentoMapper;

    private final TipoAprazamentoSearchRepository tipoAprazamentoSearchRepository;

    public TipoAprazamentoService(TipoAprazamentoRepository tipoAprazamentoRepository, TipoAprazamentoMapper tipoAprazamentoMapper, TipoAprazamentoSearchRepository tipoAprazamentoSearchRepository) {
        this.tipoAprazamentoRepository = tipoAprazamentoRepository;
        this.tipoAprazamentoMapper = tipoAprazamentoMapper;
        this.tipoAprazamentoSearchRepository = tipoAprazamentoSearchRepository;
    }

    /**
     * Save a tipoAprazamento.
     *
     * @param tipoAprazamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoAprazamentoDTO save(TipoAprazamentoDTO tipoAprazamentoDTO) {
        log.debug("Request to save TipoAprazamento : {}", tipoAprazamentoDTO);
        TipoAprazamento tipoAprazamento = tipoAprazamentoMapper.toEntity(tipoAprazamentoDTO);
        tipoAprazamento = tipoAprazamentoRepository.save(tipoAprazamento);
        TipoAprazamentoDTO result = tipoAprazamentoMapper.toDto(tipoAprazamento);
        tipoAprazamentoSearchRepository.save(tipoAprazamento);
        return result;
    }

    /**
     * Get all the tipoAprazamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoAprazamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoAprazamentos");
        return tipoAprazamentoRepository.findAll(pageable)
            .map(tipoAprazamentoMapper::toDto);
    }


    /**
     * Get one tipoAprazamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoAprazamentoDTO> findOne(Long id) {
        log.debug("Request to get TipoAprazamento : {}", id);
        return tipoAprazamentoRepository.findById(id)
            .map(tipoAprazamentoMapper::toDto);
    }

    /**
     * Delete the tipoAprazamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoAprazamento : {}", id);
        tipoAprazamentoRepository.deleteById(id);
        tipoAprazamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoAprazamento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoAprazamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoAprazamentos for query {}", query);
        return tipoAprazamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoAprazamentoMapper::toDto);
    }
}
