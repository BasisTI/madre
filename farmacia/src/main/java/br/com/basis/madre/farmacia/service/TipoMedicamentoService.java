package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.TipoMedicamento;
import br.com.basis.madre.farmacia.repository.TipoMedicamentoRepository;
import br.com.basis.madre.farmacia.repository.search.TipoMedicamentoSearchRepository;
import br.com.basis.madre.farmacia.service.dto.TipoMedicamentoDTO;
import br.com.basis.madre.farmacia.service.mapper.TipoMedicamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoMedicamento}.
 */
@Service
@Transactional
public class TipoMedicamentoService {

    private final Logger log = LoggerFactory.getLogger(TipoMedicamentoService.class);

    private final TipoMedicamentoRepository tipoMedicamentoRepository;

    private final TipoMedicamentoMapper tipoMedicamentoMapper;

    private final TipoMedicamentoSearchRepository tipoMedicamentoSearchRepository;

    public TipoMedicamentoService(TipoMedicamentoRepository tipoMedicamentoRepository, TipoMedicamentoMapper tipoMedicamentoMapper, TipoMedicamentoSearchRepository tipoMedicamentoSearchRepository) {
        this.tipoMedicamentoRepository = tipoMedicamentoRepository;
        this.tipoMedicamentoMapper = tipoMedicamentoMapper;
        this.tipoMedicamentoSearchRepository = tipoMedicamentoSearchRepository;
    }

    /**
     * Save a tipoMedicamento.
     *
     * @param tipoMedicamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoMedicamentoDTO save(TipoMedicamentoDTO tipoMedicamentoDTO) {
        log.debug("Request to save TipoMedicamento : {}", tipoMedicamentoDTO);
        TipoMedicamento tipoMedicamento = tipoMedicamentoMapper.toEntity(tipoMedicamentoDTO);
        tipoMedicamento = tipoMedicamentoRepository.save(tipoMedicamento);
        TipoMedicamentoDTO result = tipoMedicamentoMapper.toDto(tipoMedicamento);
        tipoMedicamentoSearchRepository.save(tipoMedicamento);
        return result;
    }

    /**
     * Get all the tipoMedicamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoMedicamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoMedicamentos");
        return tipoMedicamentoRepository.findAll(pageable)
            .map(tipoMedicamentoMapper::toDto);
    }


    /**
     * Get one tipoMedicamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoMedicamentoDTO> findOne(Long id) {
        log.debug("Request to get TipoMedicamento : {}", id);
        return tipoMedicamentoRepository.findById(id)
            .map(tipoMedicamentoMapper::toDto);
    }

    /**
     * Delete the tipoMedicamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoMedicamento : {}", id);
        tipoMedicamentoRepository.deleteById(id);
        tipoMedicamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoMedicamento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoMedicamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoMedicamentos for query {}", query);
        return tipoMedicamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoMedicamentoMapper::toDto);
    }
}
