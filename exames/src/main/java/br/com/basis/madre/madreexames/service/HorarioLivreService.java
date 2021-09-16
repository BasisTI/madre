package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.HorarioLivre;
import br.com.basis.madre.madreexames.repository.HorarioLivreRepository;
import br.com.basis.madre.madreexames.repository.search.HorarioLivreSearchRepository;
import br.com.basis.madre.madreexames.service.dto.HorarioLivreDTO;
import br.com.basis.madre.madreexames.service.mapper.HorarioLivreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link HorarioLivre}.
 */
@Service
@Transactional
public class HorarioLivreService {

    private final Logger log = LoggerFactory.getLogger(HorarioLivreService.class);

    private final HorarioLivreRepository horarioLivreRepository;

    private final HorarioLivreMapper horarioLivreMapper;

    private final HorarioLivreSearchRepository horarioLivreSearchRepository;

    public HorarioLivreService(HorarioLivreRepository horarioLivreRepository, HorarioLivreMapper horarioLivreMapper, HorarioLivreSearchRepository horarioLivreSearchRepository) {
        this.horarioLivreRepository = horarioLivreRepository;
        this.horarioLivreMapper = horarioLivreMapper;
        this.horarioLivreSearchRepository = horarioLivreSearchRepository;
    }

    /**
     * Save a horarioLivre.
     *
     * @param horarioLivreDTO the entity to save.
     * @return the persisted entity.
     */
    public HorarioLivreDTO save(HorarioLivreDTO horarioLivreDTO) {
        log.debug("Request to save HorarioLivre : {}", horarioLivreDTO);
        HorarioLivre horarioLivre = horarioLivreMapper.toEntity(horarioLivreDTO);
        horarioLivre = horarioLivreRepository.save(horarioLivre);
        HorarioLivreDTO result = horarioLivreMapper.toDto(horarioLivre);
        horarioLivreSearchRepository.save(horarioLivre);
        return result;
    }

    /**
     * Get all the horarioLivres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HorarioLivreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HorarioLivres");
        return horarioLivreRepository.findAll(pageable)
            .map(horarioLivreMapper::toDto);
    }


    /**
     * Get one horarioLivre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HorarioLivreDTO> findOne(Long id) {
        log.debug("Request to get HorarioLivre : {}", id);
        return horarioLivreRepository.findById(id)
            .map(horarioLivreMapper::toDto);
    }

    /**
     * Delete the horarioLivre by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HorarioLivre : {}", id);
        horarioLivreRepository.deleteById(id);
        horarioLivreSearchRepository.deleteById(id);
    }

    /**
     * Search for the horarioLivre corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HorarioLivreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HorarioLivres for query {}", query);
        return horarioLivreSearchRepository.search(queryStringQuery(query), pageable)
            .map(horarioLivreMapper::toDto);
    }
}
