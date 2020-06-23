package br.com.basis.madre.service;

import br.com.basis.madre.domain.Prescricao;
import br.com.basis.madre.repository.PrescricaoRepository;
import br.com.basis.madre.repository.search.PrescricaoSearchRepository;
import br.com.basis.madre.service.dto.PrescricaoDTO;
import br.com.basis.madre.service.mapper.PrescricaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Prescricao}.
 */
@Service
@Transactional
public class PrescricaoService {

    private final Logger log = LoggerFactory.getLogger(PrescricaoService.class);

    private final PrescricaoRepository prescricaoRepository;

    private final PrescricaoMapper prescricaoMapper;

    private final PrescricaoSearchRepository prescricaoSearchRepository;

    public PrescricaoService(PrescricaoRepository prescricaoRepository, PrescricaoMapper prescricaoMapper, PrescricaoSearchRepository prescricaoSearchRepository) {
        this.prescricaoRepository = prescricaoRepository;
        this.prescricaoMapper = prescricaoMapper;
        this.prescricaoSearchRepository = prescricaoSearchRepository;
    }

    /**
     * Save a prescricao.
     *
     * @param prescricaoDTO the entity to save.
     * @return the persisted entity.
     */
    public PrescricaoDTO save(PrescricaoDTO prescricaoDTO) {
        log.debug("Request to save Prescricao : {}", prescricaoDTO);
        Prescricao prescricao = prescricaoMapper.toEntity(prescricaoDTO);
        prescricao = prescricaoRepository.save(prescricao);
        PrescricaoDTO result = prescricaoMapper.toDto(prescricao);
        prescricaoSearchRepository.save(prescricao);
        return result;
    }

    /**
     * Get all the prescricaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prescricaos");
        return prescricaoRepository.findAll(pageable)
            .map(prescricaoMapper::toDto);
    }


    /**
     * Get one prescricao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrescricaoDTO> findOne(Long id) {
        log.debug("Request to get Prescricao : {}", id);
        return prescricaoRepository.findById(id)
            .map(prescricaoMapper::toDto);
    }

    /**
     * Delete the prescricao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Prescricao : {}", id);
        prescricaoRepository.deleteById(id);
        prescricaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the prescricao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Prescricaos for query {}", query);
        return prescricaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(prescricaoMapper::toDto);
    }
}
