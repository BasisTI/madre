package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.service.TaUsuarioUnidadeHospitalarService;
import br.com.basis.madre.cadastros.domain.TaUsuarioUnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.TaUsuarioUnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.search.TaUsuarioUnidadeHospitalarSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TaUsuarioUnidadeHospitalar.
 */
@Service
@Transactional
public class TaUsuarioUnidadeHospitalarServiceImpl implements TaUsuarioUnidadeHospitalarService {

    private final Logger log = LoggerFactory.getLogger(TaUsuarioUnidadeHospitalarServiceImpl.class);

    private final TaUsuarioUnidadeHospitalarRepository taUsuarioUnidadeHospitalarRepository;

    private final TaUsuarioUnidadeHospitalarSearchRepository taUsuarioUnidadeHospitalarSearchRepository;

    public TaUsuarioUnidadeHospitalarServiceImpl(TaUsuarioUnidadeHospitalarRepository taUsuarioUnidadeHospitalarRepository, TaUsuarioUnidadeHospitalarSearchRepository taUsuarioUnidadeHospitalarSearchRepository) {
        this.taUsuarioUnidadeHospitalarRepository = taUsuarioUnidadeHospitalarRepository;
        this.taUsuarioUnidadeHospitalarSearchRepository = taUsuarioUnidadeHospitalarSearchRepository;
    }

    /**
     * Save a taUsuarioUnidadeHospitalar.
     *
     * @param taUsuarioUnidadeHospitalar the entity to save
     * @return the persisted entity
     */
    @Override
    public TaUsuarioUnidadeHospitalar save(TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar) {
        log.debug("Request to save TaUsuarioUnidadeHospitalar : {}", taUsuarioUnidadeHospitalar);
        TaUsuarioUnidadeHospitalar result = taUsuarioUnidadeHospitalarRepository.save(taUsuarioUnidadeHospitalar);
        taUsuarioUnidadeHospitalarSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the taUsuarioUnidadeHospitalars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaUsuarioUnidadeHospitalar> findAll(Pageable pageable) {
        log.debug("Request to get all TaUsuarioUnidadeHospitalars");
        return taUsuarioUnidadeHospitalarRepository.findAll(pageable);
    }

    /**
     * Get one taUsuarioUnidadeHospitalar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TaUsuarioUnidadeHospitalar findOne(Long id) {
        log.debug("Request to get TaUsuarioUnidadeHospitalar : {}", id);
        return taUsuarioUnidadeHospitalarRepository.findOne(id);
    }

    /**
     * Delete the taUsuarioUnidadeHospitalar by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaUsuarioUnidadeHospitalar : {}", id);
        taUsuarioUnidadeHospitalarRepository.delete(id);
        taUsuarioUnidadeHospitalarSearchRepository.delete(id);
    }

    /**
     * Search for the taUsuarioUnidadeHospitalar corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaUsuarioUnidadeHospitalar> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaUsuarioUnidadeHospitalars for query {}", query);
        return taUsuarioUnidadeHospitalarSearchRepository.search(queryStringQuery(query), pageable);
    }
}
