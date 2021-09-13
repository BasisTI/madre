package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Material;
import br.com.basis.madre.madreexames.repository.MaterialRepository;
import br.com.basis.madre.madreexames.repository.search.MaterialSearchRepository;
import br.com.basis.madre.madreexames.service.dto.MaterialDTO;
import br.com.basis.madre.madreexames.service.mapper.MaterialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Material}.
 */
@Service
@Transactional
public class MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialService.class);

    private final MaterialRepository materialRepository;

    private final MaterialMapper materialMapper;

    private final MaterialSearchRepository materialSearchRepository;

    public MaterialService(MaterialRepository materialRepository, MaterialMapper materialMapper, MaterialSearchRepository materialSearchRepository) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
        this.materialSearchRepository = materialSearchRepository;
    }

    /**
     * Save a material.
     *
     * @param materialDTO the entity to save.
     * @return the persisted entity.
     */
    public MaterialDTO save(MaterialDTO materialDTO) {
        log.debug("Request to save Material : {}", materialDTO);
        Material material = materialMapper.toEntity(materialDTO);
        material = materialRepository.save(material);
        MaterialDTO result = materialMapper.toDto(material);
        materialSearchRepository.save(material);
        return result;
    }

    /**
     * Get all the materials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Materials");
        return materialRepository.findAll(pageable)
            .map(materialMapper::toDto);
    }


    /**
     * Get one material by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MaterialDTO> findOne(Long id) {
        log.debug("Request to get Material : {}", id);
        return materialRepository.findById(id)
            .map(materialMapper::toDto);
    }

    /**
     * Delete the material by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.deleteById(id);
        materialSearchRepository.deleteById(id);
    }

    /**
     * Search for the material corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MaterialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Materials for query {}", query);
        return materialSearchRepository.search(queryStringQuery(query), pageable)
            .map(materialMapper::toDto);
    }
}
