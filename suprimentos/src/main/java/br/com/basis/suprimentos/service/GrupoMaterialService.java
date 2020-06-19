package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.GrupoMaterial;
import br.com.basis.suprimentos.repository.GrupoMaterialRepository;
import br.com.basis.suprimentos.repository.search.GrupoMaterialSearchRepository;
import br.com.basis.suprimentos.service.dto.GrupoMaterialDTO;
import br.com.basis.suprimentos.service.mapper.GrupoMaterialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link GrupoMaterial}.
 */
@Service
@Transactional
public class GrupoMaterialService {

    private final Logger log = LoggerFactory.getLogger(GrupoMaterialService.class);

    private final GrupoMaterialRepository grupoMaterialRepository;

    private final GrupoMaterialMapper grupoMaterialMapper;

    private final GrupoMaterialSearchRepository grupoMaterialSearchRepository;

    public GrupoMaterialService(GrupoMaterialRepository grupoMaterialRepository, GrupoMaterialMapper grupoMaterialMapper, GrupoMaterialSearchRepository grupoMaterialSearchRepository) {
        this.grupoMaterialRepository = grupoMaterialRepository;
        this.grupoMaterialMapper = grupoMaterialMapper;
        this.grupoMaterialSearchRepository = grupoMaterialSearchRepository;
    }

    /**
     * Save a grupoMaterial.
     *
     * @param grupoMaterialDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoMaterialDTO save(GrupoMaterialDTO grupoMaterialDTO) {
        log.debug("Request to save GrupoMaterial : {}", grupoMaterialDTO);
        GrupoMaterial grupoMaterial = grupoMaterialMapper.toEntity(grupoMaterialDTO);
        grupoMaterial = grupoMaterialRepository.save(grupoMaterial);
        GrupoMaterialDTO result = grupoMaterialMapper.toDto(grupoMaterial);
        grupoMaterialSearchRepository.save(grupoMaterial);
        return result;
    }

    /**
     * Get all the grupoMaterials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupoMaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GrupoMaterials");
        return grupoMaterialRepository.findAll(pageable)
            .map(grupoMaterialMapper::toDto);
    }


    /**
     * Get one grupoMaterial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoMaterialDTO> findOne(Long id) {
        log.debug("Request to get GrupoMaterial : {}", id);
        return grupoMaterialRepository.findById(id)
            .map(grupoMaterialMapper::toDto);
    }

    /**
     * Delete the grupoMaterial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoMaterial : {}", id);
        grupoMaterialRepository.deleteById(id);
        grupoMaterialSearchRepository.deleteById(id);
    }

    /**
     * Search for the grupoMaterial corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupoMaterialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GrupoMaterials for query {}", query);
        return grupoMaterialSearchRepository.search(queryStringQuery(query), pageable)
            .map(grupoMaterialMapper::toDto);
    }
}
