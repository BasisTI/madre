package br.com.basis.madre.service;

import br.com.basis.madre.domain.Caracteristica;
import br.com.basis.madre.repository.CaracteristicaRepository;
import br.com.basis.madre.repository.search.CaracteristicaSearchRepository;
import br.com.basis.madre.service.dto.CaracteristicaDTO;
import br.com.basis.madre.service.mapper.CaracteristicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Caracteristica}.
 */
@Service
@Transactional
public class CaracteristicaService {

    private final Logger log = LoggerFactory.getLogger(CaracteristicaService.class);

    private final CaracteristicaRepository caracteristicaRepository;

    private final CaracteristicaMapper caracteristicaMapper;

    private final CaracteristicaSearchRepository caracteristicaSearchRepository;

    public CaracteristicaService(CaracteristicaRepository caracteristicaRepository, CaracteristicaMapper caracteristicaMapper, CaracteristicaSearchRepository caracteristicaSearchRepository) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.caracteristicaMapper = caracteristicaMapper;
        this.caracteristicaSearchRepository = caracteristicaSearchRepository;
    }

    /**
     * Save a caracteristica.
     *
     * @param caracteristicaDTO the entity to save.
     * @return the persisted entity.
     */
    public CaracteristicaDTO save(CaracteristicaDTO caracteristicaDTO) {
        log.debug("Request to save Caracteristica : {}", caracteristicaDTO);
        Caracteristica caracteristica = caracteristicaMapper.toEntity(caracteristicaDTO);
        caracteristica = caracteristicaRepository.save(caracteristica);
        CaracteristicaDTO result = caracteristicaMapper.toDto(caracteristica);
        caracteristicaSearchRepository.save(caracteristica);
        return result;
    }

    /**
     * Get all the caracteristicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CaracteristicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Caracteristicas");
        return caracteristicaRepository.findAll(pageable)
            .map(caracteristicaMapper::toDto);
    }


    /**
     * Get one caracteristica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CaracteristicaDTO> findOne(Long id) {
        log.debug("Request to get Caracteristica : {}", id);
        return caracteristicaRepository.findById(id)
            .map(caracteristicaMapper::toDto);
    }

    /**
     * Delete the caracteristica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Caracteristica : {}", id);
        caracteristicaRepository.deleteById(id);
        caracteristicaSearchRepository.deleteById(id);
    }

    /**
     * Search for the caracteristica corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CaracteristicaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Caracteristicas for query {}", query);
        return caracteristicaSearchRepository.search(queryStringQuery(query), pageable)
            .map(caracteristicaMapper::toDto);
    }
}
