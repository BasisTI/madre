package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.Exame;
import br.com.basis.madre.exames.repository.ExameRepository;
import br.com.basis.madre.exames.service.dto.ExameDTO;
import br.com.basis.madre.exames.service.mapper.ExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Exame}.
 */
@Service
@Transactional
public class ExameService {

    private final Logger log = LoggerFactory.getLogger(ExameService.class);

    private final ExameRepository exameRepository;

    private final ExameMapper exameMapper;

    public ExameService(ExameRepository exameRepository, ExameMapper exameMapper) {
        this.exameRepository = exameRepository;
        this.exameMapper = exameMapper;
    }

    /**
     * Save a exame.
     *
     * @param exameDTO the entity to save.
     * @return the persisted entity.
     */
    public ExameDTO save(ExameDTO exameDTO) {
        log.debug("Request to save Exame : {}", exameDTO);
        Exame exame = exameMapper.toEntity(exameDTO);
        exame = exameRepository.save(exame);
        return exameMapper.toDto(exame);
    }

    /**
     * Get all the exames.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ExameDTO> findAll() {
        log.debug("Request to get all Exames");
        return exameRepository.findAll().stream()
            .map(exameMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one exame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExameDTO> findOne(Long id) {
        log.debug("Request to get Exame : {}", id);
        return exameRepository.findById(id)
            .map(exameMapper::toDto);
    }

    /**
     * Delete the exame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Exame : {}", id);
        exameRepository.deleteById(id);
    }

    /**
     * Search for the exame corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ExameDTO> search(String query) {
        log.debug("Request to search Exames for query {}", query);
            return exameRepository.search(Exame.PREFIX, query).stream()
            .map(exameMapper::toDto)
        .collect(Collectors.toList());
    }
}
