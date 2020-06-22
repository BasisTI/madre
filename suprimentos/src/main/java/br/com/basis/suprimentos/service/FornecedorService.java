package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Fornecedor;
import br.com.basis.suprimentos.repository.FornecedorRepository;
import br.com.basis.suprimentos.repository.search.FornecedorSearchRepository;
import br.com.basis.suprimentos.service.dto.FornecedorDTO;
import br.com.basis.suprimentos.service.mapper.FornecedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Fornecedor}.
 */
@Service
@Transactional
public class FornecedorService {

    private final Logger log = LoggerFactory.getLogger(FornecedorService.class);

    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    private final FornecedorSearchRepository fornecedorSearchRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper, FornecedorSearchRepository fornecedorSearchRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
        this.fornecedorSearchRepository = fornecedorSearchRepository;
    }

    /**
     * Save a fornecedor.
     *
     * @param fornecedorDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        log.debug("Request to save Fornecedor : {}", fornecedorDTO);
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);
        fornecedor = fornecedorRepository.save(fornecedor);
        FornecedorDTO result = fornecedorMapper.toDto(fornecedor);
        fornecedorSearchRepository.save(fornecedor);
        return result;
    }

    /**
     * Get all the fornecedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fornecedors");
        return fornecedorRepository.findAll(pageable)
            .map(fornecedorMapper::toDto);
    }


    /**
     * Get one fornecedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FornecedorDTO> findOne(Long id) {
        log.debug("Request to get Fornecedor : {}", id);
        return fornecedorRepository.findById(id)
            .map(fornecedorMapper::toDto);
    }

    /**
     * Delete the fornecedor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
        fornecedorSearchRepository.deleteById(id);
    }

    /**
     * Search for the fornecedor corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fornecedors for query {}", query);
        return fornecedorSearchRepository.search(queryStringQuery(query), pageable)
            .map(fornecedorMapper::toDto);
    }
}
