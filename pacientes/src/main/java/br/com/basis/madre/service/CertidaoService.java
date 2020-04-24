package br.com.basis.madre.service;

import br.com.basis.madre.domain.Certidao;
import br.com.basis.madre.repository.CertidaoRepository;
import br.com.basis.madre.repository.search.CertidaoSearchRepository;
import br.com.basis.madre.service.dto.CertidaoDTO;
import br.com.basis.madre.service.mapper.CertidaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Certidao}.
 */
@Service
@Transactional
public class CertidaoService {

    private final Logger log = LoggerFactory.getLogger(CertidaoService.class);

    private final CertidaoRepository certidaoRepository;

    private final CertidaoMapper certidaoMapper;

    private final CertidaoSearchRepository certidaoSearchRepository;

    public CertidaoService(CertidaoRepository certidaoRepository, CertidaoMapper certidaoMapper, CertidaoSearchRepository certidaoSearchRepository) {
        this.certidaoRepository = certidaoRepository;
        this.certidaoMapper = certidaoMapper;
        this.certidaoSearchRepository = certidaoSearchRepository;
    }

    /**
     * Save a certidao.
     *
     * @param certidaoDTO the entity to save.
     * @return the persisted entity.
     */
    public CertidaoDTO save(CertidaoDTO certidaoDTO) {
        log.debug("Request to save Certidao : {}", certidaoDTO);
        Certidao certidao = certidaoMapper.toEntity(certidaoDTO);
        certidao = certidaoRepository.save(certidao);
        CertidaoDTO result = certidaoMapper.toDto(certidao);
        certidaoSearchRepository.save(certidao);
        return result;
    }

    /**
     * Get all the certidaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CertidaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Certidaos");
        return certidaoRepository.findAll(pageable)
            .map(certidaoMapper::toDto);
    }

    /**
     * Get one certidao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CertidaoDTO> findOne(Long id) {
        log.debug("Request to get Certidao : {}", id);
        return certidaoRepository.findById(id)
            .map(certidaoMapper::toDto);
    }

    /**
     * Delete the certidao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Certidao : {}", id);
        certidaoRepository.deleteById(id);
        certidaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the certidao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CertidaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Certidaos for query {}", query);
        return certidaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(certidaoMapper::toDto);
    }
}
