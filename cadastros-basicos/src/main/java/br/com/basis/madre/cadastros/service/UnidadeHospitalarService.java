package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.search.UnidadeHospitalarSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UnidadeHospitalar.
 */
@Service
@Transactional
public class UnidadeHospitalarService {

    private final Logger log = LoggerFactory.getLogger(UnidadeHospitalarService.class);

    private final UnidadeHospitalarRepository unidadeHospitalarRepository;

    private final UnidadeHospitalarSearchRepository unidadeHospitalarSearchRepository;

    public UnidadeHospitalarService(UnidadeHospitalarRepository unidadeHospitalarRepository, UnidadeHospitalarSearchRepository unidadeHospitalarSearchRepository) {
        this.unidadeHospitalarRepository = unidadeHospitalarRepository;
        this.unidadeHospitalarSearchRepository = unidadeHospitalarSearchRepository;
    }

    /**
     * Save a unidadeHospitalar.
     *
     * @param unidadeHospitalar the entity to save
     * @return the persisted entity
     */
    public UnidadeHospitalar save(UnidadeHospitalar unidadeHospitalar) {
        log.debug("Request to save UnidadeHospitalar : {}", unidadeHospitalar);
        UnidadeHospitalar result = unidadeHospitalarRepository.save(unidadeHospitalar);
        unidadeHospitalarSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the unidadeHospitalars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UnidadeHospitalar> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadeHospitalars");
        return unidadeHospitalarRepository.findAll(pageable);
    }

    /**
     * Get one unidadeHospitalar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UnidadeHospitalar findOne(Long id) {
        log.debug("Request to get UnidadeHospitalar : {}", id);
        return unidadeHospitalarRepository.findOne(id);
    }

    /**
     * Delete the unidadeHospitalar by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UnidadeHospitalar : {}", id);
        unidadeHospitalarRepository.delete(id);
        unidadeHospitalarSearchRepository.delete(id);
    }

    /**
     * Search for the unidadeHospitalar corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UnidadeHospitalar> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnidadeHospitalars for query {}", query);
        Page<UnidadeHospitalar> result = unidadeHospitalarSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
