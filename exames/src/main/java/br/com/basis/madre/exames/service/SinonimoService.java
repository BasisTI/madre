package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.Sinonimo;
import br.com.basis.madre.exames.repository.SinonimoRepository;
import br.com.basis.madre.exames.service.dto.SinonimoDTO;
import br.com.basis.madre.exames.service.mapper.SinonimoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Sinonimo}.
 */
@Service
@Transactional
public class SinonimoService {

    private final Logger log = LoggerFactory.getLogger(SinonimoService.class);

    private final SinonimoRepository sinonimoRepository;

    private final SinonimoMapper sinonimoMapper;

    public SinonimoService(SinonimoRepository sinonimoRepository, SinonimoMapper sinonimoMapper) {
        this.sinonimoRepository = sinonimoRepository;
        this.sinonimoMapper = sinonimoMapper;
    }

    /**
     * Save a sinonimo.
     *
     * @param sinonimoDTO the entity to save.
     * @return the persisted entity.
     */
    public SinonimoDTO save(SinonimoDTO sinonimoDTO) {
        log.debug("Request to save Sinonimo : {}", sinonimoDTO);
        Sinonimo sinonimo = sinonimoMapper.toEntity(sinonimoDTO);
        sinonimo = sinonimoRepository.save(sinonimo);
        return sinonimoMapper.toDto(sinonimo);
    }

    /**
     * Get all the sinonimos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SinonimoDTO> findAll() {
        log.debug("Request to get all Sinonimos");
        return sinonimoRepository.findAll().stream()
            .map(sinonimoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sinonimo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SinonimoDTO> findOne(Long id) {
        log.debug("Request to get Sinonimo : {}", id);
        return sinonimoRepository.findById(id)
            .map(sinonimoMapper::toDto);
    }

    /**
     * Delete the sinonimo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sinonimo : {}", id);
        sinonimoRepository.deleteById(id);
    }

    /**
     * Search for the sinonimo corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SinonimoDTO> search(String query) {
        log.debug("Request to search Sinonimos for query {}", query);
            return sinonimoRepository.search(Sinonimo.PREFIX, query).stream()
            .map(sinonimoMapper::toDto)
        .collect(Collectors.toList());
    }
}
