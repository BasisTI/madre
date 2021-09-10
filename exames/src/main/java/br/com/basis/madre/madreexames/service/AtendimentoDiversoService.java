package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.AtendimentoDiverso;
import br.com.basis.madre.madreexames.repository.AtendimentoDiversoRepository;
import br.com.basis.madre.madreexames.repository.search.AtendimentoDiversoSearchRepository;
import br.com.basis.madre.madreexames.service.dto.AtendimentoDiversoDTO;
import br.com.basis.madre.madreexames.service.mapper.AtendimentoDiversoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link AtendimentoDiverso}.
 */
@Service
@Transactional
public class AtendimentoDiversoService {

    private final Logger log = LoggerFactory.getLogger(AtendimentoDiversoService.class);

    private final AtendimentoDiversoRepository atendimentoDiversoRepository;

    private final AtendimentoDiversoMapper atendimentoDiversoMapper;

    private final AtendimentoDiversoSearchRepository atendimentoDiversoSearchRepository;

    public AtendimentoDiversoService(AtendimentoDiversoRepository atendimentoDiversoRepository, AtendimentoDiversoMapper atendimentoDiversoMapper, AtendimentoDiversoSearchRepository atendimentoDiversoSearchRepository) {
        this.atendimentoDiversoRepository = atendimentoDiversoRepository;
        this.atendimentoDiversoMapper = atendimentoDiversoMapper;
        this.atendimentoDiversoSearchRepository = atendimentoDiversoSearchRepository;
    }

    /**
     * Save a atendimentoDiverso.
     *
     * @param atendimentoDiversoDTO the entity to save.
     * @return the persisted entity.
     */
    public AtendimentoDiversoDTO save(AtendimentoDiversoDTO atendimentoDiversoDTO) {
        log.debug("Request to save AtendimentoDiverso : {}", atendimentoDiversoDTO);
        AtendimentoDiverso atendimentoDiverso = atendimentoDiversoMapper.toEntity(atendimentoDiversoDTO);
        atendimentoDiverso = atendimentoDiversoRepository.save(atendimentoDiverso);
        AtendimentoDiversoDTO result = atendimentoDiversoMapper.toDto(atendimentoDiverso);
        atendimentoDiversoSearchRepository.save(atendimentoDiverso);
        return result;
    }

    /**
     * Get all the atendimentoDiversos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AtendimentoDiversoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AtendimentoDiversos");
        return atendimentoDiversoRepository.findAll(pageable)
            .map(atendimentoDiversoMapper::toDto);
    }


    /**
     * Get one atendimentoDiverso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtendimentoDiversoDTO> findOne(Long id) {
        log.debug("Request to get AtendimentoDiverso : {}", id);
        return atendimentoDiversoRepository.findById(id)
            .map(atendimentoDiversoMapper::toDto);
    }

    /**
     * Delete the atendimentoDiverso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AtendimentoDiverso : {}", id);
        atendimentoDiversoRepository.deleteById(id);
        atendimentoDiversoSearchRepository.deleteById(id);
    }

    /**
     * Search for the atendimentoDiverso corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AtendimentoDiversoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AtendimentoDiversos for query {}", query);
        return atendimentoDiversoSearchRepository.search(queryStringQuery(query), pageable)
            .map(atendimentoDiversoMapper::toDto);
    }
}
