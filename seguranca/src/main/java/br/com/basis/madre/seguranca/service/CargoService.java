package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.Cargo;
import br.com.basis.madre.seguranca.repository.CargoRepository;
import br.com.basis.madre.seguranca.repository.search.CargoSearchRepository;
import br.com.basis.madre.seguranca.service.dto.CargoDTO;
import br.com.basis.madre.seguranca.service.mapper.CargoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Cargo}.
 */
@Service
@Transactional
public class CargoService {

    private final Logger log = LoggerFactory.getLogger(CargoService.class);

    private final CargoRepository cargoRepository;

    private final CargoMapper cargoMapper;

    private final CargoSearchRepository cargoSearchRepository;

    public CargoService(CargoRepository cargoRepository, CargoMapper cargoMapper, CargoSearchRepository cargoSearchRepository) {
        this.cargoRepository = cargoRepository;
        this.cargoMapper = cargoMapper;
        this.cargoSearchRepository = cargoSearchRepository;
    }

    /**
     * Save a cargo.
     *
     * @param cargoDTO the entity to save.
     * @return the persisted entity.
     */
    public CargoDTO save(CargoDTO cargoDTO) {
        log.debug("Request to save Cargo : {}", cargoDTO);
        Cargo cargo = cargoMapper.toEntity(cargoDTO);
        cargo = cargoRepository.save(cargo);
        CargoDTO result = cargoMapper.toDto(cargo);
        cargoSearchRepository.save(cargo);
        return result;
    }

    /**
     * Get all the cargos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CargoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cargos");
        return cargoRepository.findAll(pageable)
            .map(cargoMapper::toDto);
    }


    /**
     * Get one cargo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CargoDTO> findOne(Long id) {
        log.debug("Request to get Cargo : {}", id);
        return cargoRepository.findById(id)
            .map(cargoMapper::toDto);
    }

    /**
     * Delete the cargo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cargo : {}", id);
        cargoRepository.deleteById(id);
        cargoSearchRepository.deleteById(id);
    }

    /**
     * Search for the cargo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CargoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Cargos for query {}", query);
        return cargoSearchRepository.search(queryStringQuery(query), pageable)
            .map(cargoMapper::toDto);
    }
}
