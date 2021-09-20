package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Cadaver;
import br.com.basis.madre.madreexames.repository.CadaverRepository;
import br.com.basis.madre.madreexames.repository.search.CadaverSearchRepository;
import br.com.basis.madre.madreexames.service.dto.CadaverDTO;
import br.com.basis.madre.madreexames.service.mapper.CadaverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Cadaver}.
 */
@Service
@Transactional
public class CadaverService {

    private final Logger log = LoggerFactory.getLogger(CadaverService.class);

    private final CadaverRepository cadaverRepository;

    private final CadaverMapper cadaverMapper;

    private final CadaverSearchRepository cadaverSearchRepository;

    public CadaverService(CadaverRepository cadaverRepository, CadaverMapper cadaverMapper, CadaverSearchRepository cadaverSearchRepository) {
        this.cadaverRepository = cadaverRepository;
        this.cadaverMapper = cadaverMapper;
        this.cadaverSearchRepository = cadaverSearchRepository;
    }

    /**
     * Save a cadaver.
     *
     * @param cadaverDTO the entity to save.
     * @return the persisted entity.
     */
    public CadaverDTO save(CadaverDTO cadaverDTO) {
        log.debug("Request to save Cadaver : {}", cadaverDTO);
        Cadaver cadaver = cadaverMapper.toEntity(cadaverDTO);
        cadaver = cadaverRepository.save(cadaver);
        CadaverDTO result = cadaverMapper.toDto(cadaver);
        cadaverSearchRepository.save(cadaver);
        return result;
    }

    /**
     * Get all the cadavers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CadaverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cadavers");
        return cadaverRepository.findAll(pageable)
            .map(cadaverMapper::toDto);
    }


    /**
     * Get one cadaver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CadaverDTO> findOne(Long id) {
        log.debug("Request to get Cadaver : {}", id);
        return cadaverRepository.findById(id)
            .map(cadaverMapper::toDto);
    }

    /**
     * Delete the cadaver by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cadaver : {}", id);
        cadaverRepository.deleteById(id);
        cadaverSearchRepository.deleteById(id);
    }

    /**
     * Search for the cadaver corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CadaverDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Cadavers for query {}", query);
        return cadaverSearchRepository.search(queryStringQuery(query), pageable)
            .map(cadaverMapper::toDto);
    }
}
