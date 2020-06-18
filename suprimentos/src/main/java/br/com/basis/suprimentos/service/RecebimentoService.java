package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Recebimento;
import br.com.basis.suprimentos.repository.RecebimentoRepository;
import br.com.basis.suprimentos.repository.search.RecebimentoSearchRepository;
import br.com.basis.suprimentos.service.dto.RecebimentoDTO;
import br.com.basis.suprimentos.service.mapper.RecebimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Recebimento}.
 */
@Service
@Transactional
public class RecebimentoService {

    private final Logger log = LoggerFactory.getLogger(RecebimentoService.class);

    private final RecebimentoRepository recebimentoRepository;

    private final RecebimentoMapper recebimentoMapper;

    private final RecebimentoSearchRepository recebimentoSearchRepository;

    public RecebimentoService(RecebimentoRepository recebimentoRepository, RecebimentoMapper recebimentoMapper, RecebimentoSearchRepository recebimentoSearchRepository) {
        this.recebimentoRepository = recebimentoRepository;
        this.recebimentoMapper = recebimentoMapper;
        this.recebimentoSearchRepository = recebimentoSearchRepository;
    }

    /**
     * Save a recebimento.
     *
     * @param recebimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public RecebimentoDTO save(RecebimentoDTO recebimentoDTO) {
        log.debug("Request to save Recebimento : {}", recebimentoDTO);
        Recebimento recebimento = recebimentoMapper.toEntity(recebimentoDTO);
        recebimento = recebimentoRepository.save(recebimento);
        RecebimentoDTO result = recebimentoMapper.toDto(recebimento);
        recebimentoSearchRepository.save(recebimento);
        return result;
    }

    /**
     * Get all the recebimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recebimentos");
        return recebimentoRepository.findAll(pageable)
            .map(recebimentoMapper::toDto);
    }


    /**
     * Get one recebimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecebimentoDTO> findOne(Long id) {
        log.debug("Request to get Recebimento : {}", id);
        return recebimentoRepository.findById(id)
            .map(recebimentoMapper::toDto);
    }

    /**
     * Delete the recebimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recebimento : {}", id);
        recebimentoRepository.deleteById(id);
        recebimentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the recebimento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Recebimentos for query {}", query);
        return recebimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(recebimentoMapper::toDto);
    }
}
