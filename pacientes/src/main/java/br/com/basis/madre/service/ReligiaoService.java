package br.com.basis.madre.service;

import br.com.basis.madre.domain.Religiao;
import br.com.basis.madre.repository.ReligiaoRepository;
import br.com.basis.madre.repository.search.ReligiaoSearchRepository;
import br.com.basis.madre.service.dto.ReligiaoDTO;
import br.com.basis.madre.service.mapper.ReligiaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Religiao}.
 */
@Service
@Transactional
public class ReligiaoService {

    private final Logger log = LoggerFactory.getLogger(ReligiaoService.class);

    private final ReligiaoRepository religiaoRepository;

    private final ReligiaoMapper religiaoMapper;

    private final ReligiaoSearchRepository religiaoSearchRepository;

    public ReligiaoService(ReligiaoRepository religiaoRepository, ReligiaoMapper religiaoMapper, ReligiaoSearchRepository religiaoSearchRepository) {
        this.religiaoRepository = religiaoRepository;
        this.religiaoMapper = religiaoMapper;
        this.religiaoSearchRepository = religiaoSearchRepository;
    }

    /**
     * Save a religiao.
     *
     * @param religiaoDTO the entity to save.
     * @return the persisted entity.
     */
    public ReligiaoDTO save(ReligiaoDTO religiaoDTO) {
        log.debug("Request to save Religiao : {}", religiaoDTO);
        Religiao religiao = religiaoMapper.toEntity(religiaoDTO);
        religiao = religiaoRepository.save(religiao);
        ReligiaoDTO result = religiaoMapper.toDto(religiao);
        religiaoSearchRepository.save(religiao);
        return result;
    }

    /**
     * Get all the religiaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReligiaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Religiaos");
        return religiaoRepository.findAll(pageable)
            .map(religiaoMapper::toDto);
    }

    /**
     * Get one religiao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReligiaoDTO> findOne(Long id) {
        log.debug("Request to get Religiao : {}", id);
        return religiaoRepository.findById(id)
            .map(religiaoMapper::toDto);
    }

    /**
     * Delete the religiao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Religiao : {}", id);
        religiaoRepository.deleteById(id);
        religiaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the religiao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReligiaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Religiaos for query {}", query);
        return religiaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(religiaoMapper::toDto);
    }
}
