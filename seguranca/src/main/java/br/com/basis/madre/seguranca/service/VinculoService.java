package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.Vinculo;
import br.com.basis.madre.seguranca.repository.VinculoRepository;
import br.com.basis.madre.seguranca.repository.search.VinculoSearchRepository;
import br.com.basis.madre.seguranca.service.dto.VinculoDTO;
import br.com.basis.madre.seguranca.service.mapper.VinculoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Vinculo}.
 */
@Service
@Transactional
public class VinculoService {

    private final Logger log = LoggerFactory.getLogger(VinculoService.class);

    private final VinculoRepository vinculoRepository;

    private final VinculoMapper vinculoMapper;

    private final VinculoSearchRepository vinculoSearchRepository;

    public VinculoService(VinculoRepository vinculoRepository, VinculoMapper vinculoMapper, VinculoSearchRepository vinculoSearchRepository) {
        this.vinculoRepository = vinculoRepository;
        this.vinculoMapper = vinculoMapper;
        this.vinculoSearchRepository = vinculoSearchRepository;
    }

    /**
     * Save a vinculo.
     *
     * @param vinculoDTO the entity to save.
     * @return the persisted entity.
     */
    public VinculoDTO save(VinculoDTO vinculoDTO) {
        log.debug("Request to save Vinculo : {}", vinculoDTO);
        Vinculo vinculo = vinculoMapper.toEntity(vinculoDTO);
        vinculo = vinculoRepository.save(vinculo);
        VinculoDTO result = vinculoMapper.toDto(vinculo);
        vinculoSearchRepository.save(vinculo);
        return result;
    }

    /**
     * Get all the vinculos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VinculoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vinculos");
        return vinculoRepository.findAll(pageable)
            .map(vinculoMapper::toDto);
    }


    /**
     * Get one vinculo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VinculoDTO> findOne(Long id) {
        log.debug("Request to get Vinculo : {}", id);
        return vinculoRepository.findById(id)
            .map(vinculoMapper::toDto);
    }

    /**
     * Delete the vinculo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vinculo : {}", id);
        vinculoRepository.deleteById(id);
        vinculoSearchRepository.deleteById(id);
    }

    /**
     * Search for the vinculo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VinculoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Vinculos for query {}", query);
        return vinculoSearchRepository.search(queryStringQuery(query), pageable)
            .map(vinculoMapper::toDto);
    }
}
