package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.Graduacao;
import br.com.basis.madre.seguranca.repository.GraduacaoRepository;
import br.com.basis.madre.seguranca.repository.search.GraduacaoSearchRepository;
import br.com.basis.madre.seguranca.service.dto.GraduacaoDTO;
import br.com.basis.madre.seguranca.service.mapper.GraduacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Graduacao}.
 */
@Service
@Transactional
public class GraduacaoService {

    private final Logger log = LoggerFactory.getLogger(GraduacaoService.class);

    private final GraduacaoRepository graduacaoRepository;

    private final GraduacaoMapper graduacaoMapper;

    private final GraduacaoSearchRepository graduacaoSearchRepository;

    public GraduacaoService(GraduacaoRepository graduacaoRepository, GraduacaoMapper graduacaoMapper, GraduacaoSearchRepository graduacaoSearchRepository) {
        this.graduacaoRepository = graduacaoRepository;
        this.graduacaoMapper = graduacaoMapper;
        this.graduacaoSearchRepository = graduacaoSearchRepository;
    }

    /**
     * Save a graduacao.
     *
     * @param graduacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public GraduacaoDTO save(GraduacaoDTO graduacaoDTO) {
        log.debug("Request to save Graduacao : {}", graduacaoDTO);
        Graduacao graduacao = graduacaoMapper.toEntity(graduacaoDTO);
        graduacao = graduacaoRepository.save(graduacao);
        GraduacaoDTO result = graduacaoMapper.toDto(graduacao);
        graduacaoSearchRepository.save(graduacao);
        return result;
    }

    /**
     * Get all the graduacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GraduacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Graduacaos");
        return graduacaoRepository.findAll(pageable)
            .map(graduacaoMapper::toDto);
    }


    /**
     * Get one graduacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GraduacaoDTO> findOne(Long id) {
        log.debug("Request to get Graduacao : {}", id);
        return graduacaoRepository.findById(id)
            .map(graduacaoMapper::toDto);
    }

    /**
     * Delete the graduacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Graduacao : {}", id);
        graduacaoRepository.deleteById(id);
        graduacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the graduacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GraduacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Graduacaos for query {}", query);
        return graduacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(graduacaoMapper::toDto);
    }
}
