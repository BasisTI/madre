package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.Motivo;
import br.com.basis.madre.farmacia.repository.MotivoRepository;
import br.com.basis.madre.farmacia.repository.search.MotivoSearchRepository;
import br.com.basis.madre.farmacia.service.dto.MotivoDTO;
import br.com.basis.madre.farmacia.service.mapper.MotivoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Motivo}.
 */
@Service
@Transactional
public class MotivoService {

    private final Logger log = LoggerFactory.getLogger(MotivoService.class);

    private final MotivoRepository motivoRepository;

    private final MotivoMapper motivoMapper;

    private final MotivoSearchRepository motivoSearchRepository;

    public MotivoService(MotivoRepository motivoRepository, MotivoMapper motivoMapper, MotivoSearchRepository motivoSearchRepository) {
        this.motivoRepository = motivoRepository;
        this.motivoMapper = motivoMapper;
        this.motivoSearchRepository = motivoSearchRepository;
    }

    /**
     * Save a motivo.
     *
     * @param motivoDTO the entity to save.
     * @return the persisted entity.
     */
    public MotivoDTO save(MotivoDTO motivoDTO) {
        log.debug("Request to save Motivo : {}", motivoDTO);
        Motivo motivo = motivoMapper.toEntity(motivoDTO);
        motivo = motivoRepository.save(motivo);
        MotivoDTO result = motivoMapper.toDto(motivo);
        motivoSearchRepository.save(motivo);
        return result;
    }

    /**
     * Get all the motivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MotivoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Motivos");
        return motivoRepository.findAll(pageable)
            .map(motivoMapper::toDto);
    }


    /**
     * Get one motivo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MotivoDTO> findOne(Long id) {
        log.debug("Request to get Motivo : {}", id);
        return motivoRepository.findById(id)
            .map(motivoMapper::toDto);
    }

    /**
     * Delete the motivo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Motivo : {}", id);
        motivoRepository.deleteById(id);
        motivoSearchRepository.deleteById(id);
    }

    /**
     * Search for the motivo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MotivoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Motivos for query {}", query);
        return motivoSearchRepository.search(queryStringQuery(query), pageable)
            .map(motivoMapper::toDto);
    }
}
