package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.LaboratorioExterno;
import br.com.basis.madre.madreexames.repository.LaboratorioExternoRepository;
import br.com.basis.madre.madreexames.repository.search.LaboratorioExternoSearchRepository;
import br.com.basis.madre.madreexames.service.dto.LaboratorioExternoDTO;
import br.com.basis.madre.madreexames.service.mapper.LaboratorioExternoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link LaboratorioExterno}.
 */
@Service
@Transactional
public class LaboratorioExternoService {

    private final Logger log = LoggerFactory.getLogger(LaboratorioExternoService.class);

    private final LaboratorioExternoRepository laboratorioExternoRepository;

    private final LaboratorioExternoMapper laboratorioExternoMapper;

    private final LaboratorioExternoSearchRepository laboratorioExternoSearchRepository;

    public LaboratorioExternoService(LaboratorioExternoRepository laboratorioExternoRepository, LaboratorioExternoMapper laboratorioExternoMapper, LaboratorioExternoSearchRepository laboratorioExternoSearchRepository) {
        this.laboratorioExternoRepository = laboratorioExternoRepository;
        this.laboratorioExternoMapper = laboratorioExternoMapper;
        this.laboratorioExternoSearchRepository = laboratorioExternoSearchRepository;
    }

    /**
     * Save a laboratorioExterno.
     *
     * @param laboratorioExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public LaboratorioExternoDTO save(LaboratorioExternoDTO laboratorioExternoDTO) {
        log.debug("Request to save LaboratorioExterno : {}", laboratorioExternoDTO);
        LaboratorioExterno laboratorioExterno = laboratorioExternoMapper.toEntity(laboratorioExternoDTO);
        laboratorioExterno = laboratorioExternoRepository.save(laboratorioExterno);
        LaboratorioExternoDTO result = laboratorioExternoMapper.toDto(laboratorioExterno);
        laboratorioExternoSearchRepository.save(laboratorioExterno);
        return result;
    }

    /**
     * Get all the laboratorioExternos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LaboratorioExternoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LaboratorioExternos");
        return laboratorioExternoRepository.findAll(pageable)
            .map(laboratorioExternoMapper::toDto);
    }


    /**
     * Get one laboratorioExterno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LaboratorioExternoDTO> findOne(Long id) {
        log.debug("Request to get LaboratorioExterno : {}", id);
        return laboratorioExternoRepository.findById(id)
            .map(laboratorioExternoMapper::toDto);
    }

    /**
     * Delete the laboratorioExterno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LaboratorioExterno : {}", id);
        laboratorioExternoRepository.deleteById(id);
        laboratorioExternoSearchRepository.deleteById(id);
    }

    /**
     * Search for the laboratorioExterno corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LaboratorioExternoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LaboratorioExternos for query {}", query);
        return laboratorioExternoSearchRepository.search(queryStringQuery(query), pageable)
            .map(laboratorioExternoMapper::toDto);
    }
}
