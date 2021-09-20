package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.MaterialDeExame;
import br.com.basis.madre.madreexames.repository.MaterialDeExameRepository;
import br.com.basis.madre.madreexames.repository.search.MaterialDeExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.MaterialDeExameCompletoDTO;
import br.com.basis.madre.madreexames.service.dto.MaterialDeExameDTO;
import br.com.basis.madre.madreexames.service.mapper.MaterialDeExameCompletoMapper;
import br.com.basis.madre.madreexames.service.mapper.MaterialDeExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link MaterialDeExame}.
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Transactional
public class MaterialDeExameService {

    private final Logger log = LoggerFactory.getLogger(MaterialDeExameService.class);

    private final MaterialDeExameRepository materialDeExameRepository;

    private final MaterialDeExameMapper materialDeExameMapper;

    private final MaterialDeExameSearchRepository materialDeExameSearchRepository;

    private final MaterialDeExameCompletoMapper materialDeExameCompletoMapper;

    public MaterialDeExameService(MaterialDeExameRepository materialDeExameRepository, MaterialDeExameMapper materialDeExameMapper, MaterialDeExameSearchRepository materialDeExameSearchRepository, MaterialDeExameCompletoMapper materialDeExameCompletoMapper) {

        this.materialDeExameRepository = materialDeExameRepository;
        this.materialDeExameMapper = materialDeExameMapper;
        this.materialDeExameSearchRepository = materialDeExameSearchRepository;
        this.materialDeExameCompletoMapper = materialDeExameCompletoMapper;
    }

    /**
     * Save a materialDeExame.
     *
     * @param materialDeExameCompletoDTO the entity to save.
     * @return the persisted entity.
     */
    public MaterialDeExameCompletoDTO save(MaterialDeExameCompletoDTO materialDeExameCompletoDTO) {
        log.debug("Request to save MaterialDeExame : {}", materialDeExameCompletoDTO);
        MaterialDeExame materialDeExame = materialDeExameCompletoMapper.toEntity(materialDeExameCompletoDTO);
        materialDeExame = materialDeExameRepository.save(materialDeExame);
        MaterialDeExameCompletoDTO result = materialDeExameCompletoMapper.toDto(materialDeExame);
        materialDeExameSearchRepository.save(materialDeExame);
        return result;
    }

    /**
     * Get all the materialDeExames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MaterialDeExameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MaterialDeExames");
        return materialDeExameRepository.findAll(pageable)
            .map(materialDeExameMapper::toDto);
    }


    /**
     * Get one materialDeExame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MaterialDeExameDTO> findOne(Long id) {
        log.debug("Request to get MaterialDeExame : {}", id);
        return materialDeExameRepository.findById(id)
            .map(materialDeExameMapper::toDto);
    }

    /**
     * Delete the materialDeExame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MaterialDeExame : {}", id);
        materialDeExameRepository.deleteById(id);
        materialDeExameSearchRepository.deleteById(id);
    }

    /**
     * Search for the materialDeExame corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MaterialDeExameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MaterialDeExames for query {}", query);
        return materialDeExameSearchRepository.search(queryStringQuery(query), pageable)
            .map(materialDeExameMapper::toDto);
    }
}
