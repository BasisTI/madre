package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.CaracteristicaArmazenamento;
import br.com.basis.suprimentos.repository.CaracteristicaArmazenamentoRepository;
import br.com.basis.suprimentos.repository.search.CaracteristicaArmazenamentoSearchRepository;
import br.com.basis.suprimentos.service.dto.CaracteristicaArmazenamentoDTO;
import br.com.basis.suprimentos.service.mapper.CaracteristicaArmazenamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CaracteristicaArmazenamento}.
 */
@Service
@Transactional
public class CaracteristicaArmazenamentoService {

    private final Logger log = LoggerFactory.getLogger(CaracteristicaArmazenamentoService.class);

    private final CaracteristicaArmazenamentoRepository caracteristicaArmazenamentoRepository;

    private final CaracteristicaArmazenamentoMapper caracteristicaArmazenamentoMapper;

    private final CaracteristicaArmazenamentoSearchRepository caracteristicaArmazenamentoSearchRepository;

    public CaracteristicaArmazenamentoService(CaracteristicaArmazenamentoRepository caracteristicaArmazenamentoRepository, CaracteristicaArmazenamentoMapper caracteristicaArmazenamentoMapper, CaracteristicaArmazenamentoSearchRepository caracteristicaArmazenamentoSearchRepository) {
        this.caracteristicaArmazenamentoRepository = caracteristicaArmazenamentoRepository;
        this.caracteristicaArmazenamentoMapper = caracteristicaArmazenamentoMapper;
        this.caracteristicaArmazenamentoSearchRepository = caracteristicaArmazenamentoSearchRepository;
    }

    /**
     * Save a caracteristicaArmazenamento.
     *
     * @param caracteristicaArmazenamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public CaracteristicaArmazenamentoDTO save(CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO) {
        log.debug("Request to save CaracteristicaArmazenamento : {}", caracteristicaArmazenamentoDTO);
        CaracteristicaArmazenamento caracteristicaArmazenamento = caracteristicaArmazenamentoMapper.toEntity(caracteristicaArmazenamentoDTO);
        caracteristicaArmazenamento = caracteristicaArmazenamentoRepository.save(caracteristicaArmazenamento);
        CaracteristicaArmazenamentoDTO result = caracteristicaArmazenamentoMapper.toDto(caracteristicaArmazenamento);
        caracteristicaArmazenamentoSearchRepository.save(caracteristicaArmazenamento);
        return result;
    }

    /**
     * Get all the caracteristicaArmazenamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CaracteristicaArmazenamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CaracteristicaArmazenamentos");
        return caracteristicaArmazenamentoRepository.findAll(pageable)
            .map(caracteristicaArmazenamentoMapper::toDto);
    }


    /**
     * Get one caracteristicaArmazenamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CaracteristicaArmazenamentoDTO> findOne(Long id) {
        log.debug("Request to get CaracteristicaArmazenamento : {}", id);
        return caracteristicaArmazenamentoRepository.findById(id)
            .map(caracteristicaArmazenamentoMapper::toDto);
    }

    /**
     * Delete the caracteristicaArmazenamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CaracteristicaArmazenamento : {}", id);
        caracteristicaArmazenamentoRepository.deleteById(id);
        caracteristicaArmazenamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the caracteristicaArmazenamento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CaracteristicaArmazenamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CaracteristicaArmazenamentos for query {}", query);
        return caracteristicaArmazenamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(caracteristicaArmazenamentoMapper::toDto);
    }
}
