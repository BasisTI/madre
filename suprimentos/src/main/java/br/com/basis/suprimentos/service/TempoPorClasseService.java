package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.TempoPorClasse;
import br.com.basis.suprimentos.repository.TempoPorClasseRepository;
import br.com.basis.suprimentos.repository.search.TempoPorClasseSearchRepository;
import br.com.basis.suprimentos.service.dto.TempoPorClasseDTO;
import br.com.basis.suprimentos.service.mapper.TempoPorClasseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TempoPorClasse}.
 */
@Service
@Transactional
public class TempoPorClasseService {

    private final Logger log = LoggerFactory.getLogger(TempoPorClasseService.class);

    private final TempoPorClasseRepository tempoPorClasseRepository;

    private final TempoPorClasseMapper tempoPorClasseMapper;

    private final TempoPorClasseSearchRepository tempoPorClasseSearchRepository;

    public TempoPorClasseService(TempoPorClasseRepository tempoPorClasseRepository, TempoPorClasseMapper tempoPorClasseMapper, TempoPorClasseSearchRepository tempoPorClasseSearchRepository) {
        this.tempoPorClasseRepository = tempoPorClasseRepository;
        this.tempoPorClasseMapper = tempoPorClasseMapper;
        this.tempoPorClasseSearchRepository = tempoPorClasseSearchRepository;
    }

    /**
     * Save a tempoPorClasse.
     *
     * @param tempoPorClasseDTO the entity to save.
     * @return the persisted entity.
     */
    public TempoPorClasseDTO save(TempoPorClasseDTO tempoPorClasseDTO) {
        log.debug("Request to save TempoPorClasse : {}", tempoPorClasseDTO);
        TempoPorClasse tempoPorClasse = tempoPorClasseMapper.toEntity(tempoPorClasseDTO);
        tempoPorClasse = tempoPorClasseRepository.save(tempoPorClasse);
        TempoPorClasseDTO result = tempoPorClasseMapper.toDto(tempoPorClasse);
        tempoPorClasseSearchRepository.save(tempoPorClasse);
        return result;
    }

    /**
     * Get all the tempoPorClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TempoPorClasseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TempoPorClasses");
        return tempoPorClasseRepository.findAll(pageable)
            .map(tempoPorClasseMapper::toDto);
    }


    /**
     * Get one tempoPorClasse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TempoPorClasseDTO> findOne(Long id) {
        log.debug("Request to get TempoPorClasse : {}", id);
        return tempoPorClasseRepository.findById(id)
            .map(tempoPorClasseMapper::toDto);
    }

    /**
     * Delete the tempoPorClasse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TempoPorClasse : {}", id);
        tempoPorClasseRepository.deleteById(id);
        tempoPorClasseSearchRepository.deleteById(id);
    }

    /**
     * Search for the tempoPorClasse corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TempoPorClasseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TempoPorClasses for query {}", query);
        return tempoPorClasseSearchRepository.search(queryStringQuery(query), pageable)
            .map(tempoPorClasseMapper::toDto);
    }
}
