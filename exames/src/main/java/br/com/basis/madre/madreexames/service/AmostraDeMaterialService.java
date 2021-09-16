package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.AmostraDeMaterial;
import br.com.basis.madre.madreexames.repository.AmostraDeMaterialRepository;
import br.com.basis.madre.madreexames.repository.search.AmostraDeMaterialSearchRepository;
import br.com.basis.madre.madreexames.service.dto.AmostraDeMaterialDTO;
import br.com.basis.madre.madreexames.service.mapper.AmostraDeMaterialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link AmostraDeMaterial}.
 */
@Service
@Transactional
public class AmostraDeMaterialService {

    private final Logger log = LoggerFactory.getLogger(AmostraDeMaterialService.class);

    private final AmostraDeMaterialRepository amostraDeMaterialRepository;

    private final AmostraDeMaterialMapper amostraDeMaterialMapper;

    private final AmostraDeMaterialSearchRepository amostraDeMaterialSearchRepository;

    public AmostraDeMaterialService(AmostraDeMaterialRepository amostraDeMaterialRepository, AmostraDeMaterialMapper amostraDeMaterialMapper, AmostraDeMaterialSearchRepository amostraDeMaterialSearchRepository) {
        this.amostraDeMaterialRepository = amostraDeMaterialRepository;
        this.amostraDeMaterialMapper = amostraDeMaterialMapper;
        this.amostraDeMaterialSearchRepository = amostraDeMaterialSearchRepository;
    }

    /**
     * Save a amostraDeMaterial.
     *
     * @param amostraDeMaterialDTO the entity to save.
     * @return the persisted entity.
     */
    public AmostraDeMaterialDTO save(AmostraDeMaterialDTO amostraDeMaterialDTO) {
        log.debug("Request to save AmostraDeMaterial : {}", amostraDeMaterialDTO);
        AmostraDeMaterial amostraDeMaterial = amostraDeMaterialMapper.toEntity(amostraDeMaterialDTO);
        amostraDeMaterial = amostraDeMaterialRepository.save(amostraDeMaterial);
        AmostraDeMaterialDTO result = amostraDeMaterialMapper.toDto(amostraDeMaterial);
        amostraDeMaterialSearchRepository.save(amostraDeMaterial);
        return result;
    }

    /**
     * Get all the amostraDeMaterials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AmostraDeMaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AmostraDeMaterials");
        return amostraDeMaterialRepository.findAll(pageable)
            .map(amostraDeMaterialMapper::toDto);
    }


    /**
     * Get one amostraDeMaterial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AmostraDeMaterialDTO> findOne(Long id) {
        log.debug("Request to get AmostraDeMaterial : {}", id);
        return amostraDeMaterialRepository.findById(id)
            .map(amostraDeMaterialMapper::toDto);
    }

    /**
     * Delete the amostraDeMaterial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AmostraDeMaterial : {}", id);
        amostraDeMaterialRepository.deleteById(id);
        amostraDeMaterialSearchRepository.deleteById(id);
    }

    /**
     * Search for the amostraDeMaterial corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AmostraDeMaterialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AmostraDeMaterials for query {}", query);
        return amostraDeMaterialSearchRepository.search(queryStringQuery(query), pageable)
            .map(amostraDeMaterialMapper::toDto);
    }
}
