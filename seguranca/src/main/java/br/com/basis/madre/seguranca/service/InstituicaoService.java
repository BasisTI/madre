package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.Instituicao;
import br.com.basis.madre.seguranca.repository.InstituicaoRepository;
import br.com.basis.madre.seguranca.repository.search.InstituicaoSearchRepository;
import br.com.basis.madre.seguranca.service.dto.InstituicaoDTO;
import br.com.basis.madre.seguranca.service.mapper.InstituicaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Instituicao}.
 */
@Service
@Transactional
public class InstituicaoService {

    private final Logger log = LoggerFactory.getLogger(InstituicaoService.class);

    private final InstituicaoRepository instituicaoRepository;

    private final InstituicaoMapper instituicaoMapper;

    private final InstituicaoSearchRepository instituicaoSearchRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository, InstituicaoMapper instituicaoMapper, InstituicaoSearchRepository instituicaoSearchRepository) {
        this.instituicaoRepository = instituicaoRepository;
        this.instituicaoMapper = instituicaoMapper;
        this.instituicaoSearchRepository = instituicaoSearchRepository;
    }

    /**
     * Save a instituicao.
     *
     * @param instituicaoDTO the entity to save.
     * @return the persisted entity.
     */
    public InstituicaoDTO save(InstituicaoDTO instituicaoDTO) {
        log.debug("Request to save Instituicao : {}", instituicaoDTO);
        Instituicao instituicao = instituicaoMapper.toEntity(instituicaoDTO);
        instituicao = instituicaoRepository.save(instituicao);
        InstituicaoDTO result = instituicaoMapper.toDto(instituicao);
        instituicaoSearchRepository.save(instituicao);
        return result;
    }

    /**
     * Get all the instituicaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InstituicaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Instituicaos");
        return instituicaoRepository.findAll(pageable)
            .map(instituicaoMapper::toDto);
    }


    /**
     * Get one instituicao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InstituicaoDTO> findOne(Long id) {
        log.debug("Request to get Instituicao : {}", id);
        return instituicaoRepository.findById(id)
            .map(instituicaoMapper::toDto);
    }

    /**
     * Delete the instituicao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Instituicao : {}", id);
        instituicaoRepository.deleteById(id);
        instituicaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the instituicao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InstituicaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Instituicaos for query {}", query);
        return instituicaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(instituicaoMapper::toDto);
    }
}
