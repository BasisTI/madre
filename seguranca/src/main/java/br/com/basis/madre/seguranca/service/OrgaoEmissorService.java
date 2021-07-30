package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.OrgaoEmissor;
import br.com.basis.madre.seguranca.repository.OrgaoEmissorRepository;
import br.com.basis.madre.seguranca.repository.search.OrgaoEmissorSearchRepository;
import br.com.basis.madre.seguranca.service.dto.OrgaoEmissorDTO;
import br.com.basis.madre.seguranca.service.mapper.OrgaoEmissorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link OrgaoEmissor}.
 */
@Service
@Transactional
public class OrgaoEmissorService {

    private final Logger log = LoggerFactory.getLogger(OrgaoEmissorService.class);

    private final OrgaoEmissorRepository orgaoEmissorRepository;

    private final OrgaoEmissorMapper orgaoEmissorMapper;

    private final OrgaoEmissorSearchRepository orgaoEmissorSearchRepository;

    public OrgaoEmissorService(OrgaoEmissorRepository orgaoEmissorRepository, OrgaoEmissorMapper orgaoEmissorMapper, OrgaoEmissorSearchRepository orgaoEmissorSearchRepository) {
        this.orgaoEmissorRepository = orgaoEmissorRepository;
        this.orgaoEmissorMapper = orgaoEmissorMapper;
        this.orgaoEmissorSearchRepository = orgaoEmissorSearchRepository;
    }

    /**
     * Save a orgaoEmissor.
     *
     * @param orgaoEmissorDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgaoEmissorDTO save(OrgaoEmissorDTO orgaoEmissorDTO) {
        log.debug("Request to save OrgaoEmissor : {}", orgaoEmissorDTO);
        OrgaoEmissor orgaoEmissor = orgaoEmissorMapper.toEntity(orgaoEmissorDTO);
        orgaoEmissor = orgaoEmissorRepository.save(orgaoEmissor);
        OrgaoEmissorDTO result = orgaoEmissorMapper.toDto(orgaoEmissor);
        orgaoEmissorSearchRepository.save(orgaoEmissor);
        return result;
    }

    /**
     * Get all the orgaoEmissors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgaoEmissorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrgaoEmissors");
        return orgaoEmissorRepository.findAll(pageable)
            .map(orgaoEmissorMapper::toDto);
    }


    /**
     * Get one orgaoEmissor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrgaoEmissorDTO> findOne(Long id) {
        log.debug("Request to get OrgaoEmissor : {}", id);
        return orgaoEmissorRepository.findById(id)
            .map(orgaoEmissorMapper::toDto);
    }

    /**
     * Delete the orgaoEmissor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrgaoEmissor : {}", id);
        orgaoEmissorRepository.deleteById(id);
        orgaoEmissorSearchRepository.deleteById(id);
    }

    /**
     * Search for the orgaoEmissor corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgaoEmissorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrgaoEmissors for query {}", query);
        return orgaoEmissorSearchRepository.search(queryStringQuery(query), pageable)
            .map(orgaoEmissorMapper::toDto);
    }
}
