package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.TaUsuarioUnidadeHospitalar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TaUsuarioUnidadeHospitalar.
 */
public interface TaUsuarioUnidadeHospitalarService {

    /**
     * Save a taUsuarioUnidadeHospitalar.
     *
     * @param taUsuarioUnidadeHospitalar the entity to save
     * @return the persisted entity
     */
    TaUsuarioUnidadeHospitalar save(TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar);

    /**
     * Get all the taUsuarioUnidadeHospitalars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TaUsuarioUnidadeHospitalar> findAll(Pageable pageable);

    /**
     * Get the "id" taUsuarioUnidadeHospitalar.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TaUsuarioUnidadeHospitalar findOne(Long id);

    /**
     * Delete the "id" taUsuarioUnidadeHospitalar.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the taUsuarioUnidadeHospitalar corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TaUsuarioUnidadeHospitalar> search(String query, Pageable pageable);
}
