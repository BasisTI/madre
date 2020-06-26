package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.DispensacaoMedicamentos;
import br.com.basis.madre.farmacia.repository.DispensacaoMedicamentosRepository;
import br.com.basis.madre.farmacia.repository.search.DispensacaoMedicamentosSearchRepository;
import br.com.basis.madre.farmacia.service.dto.DispensacaoMedicamentosDTO;
import br.com.basis.madre.farmacia.service.mapper.DispensacaoMedicamentosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DispensacaoMedicamentos}.
 */
@Service
@Transactional
public class DispensacaoMedicamentosService {

    private final Logger log = LoggerFactory.getLogger(DispensacaoMedicamentosService.class);

    private final DispensacaoMedicamentosRepository dispensacaoMedicamentosRepository;

    private final DispensacaoMedicamentosMapper dispensacaoMedicamentosMapper;

    private final DispensacaoMedicamentosSearchRepository dispensacaoMedicamentosSearchRepository;

    public DispensacaoMedicamentosService(DispensacaoMedicamentosRepository dispensacaoMedicamentosRepository, DispensacaoMedicamentosMapper dispensacaoMedicamentosMapper, DispensacaoMedicamentosSearchRepository dispensacaoMedicamentosSearchRepository) {
        this.dispensacaoMedicamentosRepository = dispensacaoMedicamentosRepository;
        this.dispensacaoMedicamentosMapper = dispensacaoMedicamentosMapper;
        this.dispensacaoMedicamentosSearchRepository = dispensacaoMedicamentosSearchRepository;
    }

    /**
     * Save a dispensacaoMedicamentos.
     *
     * @param dispensacaoMedicamentosDTO the entity to save.
     * @return the persisted entity.
     */
    public DispensacaoMedicamentosDTO save(DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO) {
        log.debug("Request to save DispensacaoMedicamentos : {}", dispensacaoMedicamentosDTO);
        DispensacaoMedicamentos dispensacaoMedicamentos = dispensacaoMedicamentosMapper.toEntity(dispensacaoMedicamentosDTO);
        dispensacaoMedicamentos = dispensacaoMedicamentosRepository.save(dispensacaoMedicamentos);
        DispensacaoMedicamentosDTO result = dispensacaoMedicamentosMapper.toDto(dispensacaoMedicamentos);
        dispensacaoMedicamentosSearchRepository.save(dispensacaoMedicamentos);
        return result;
    }

    /**
     * Get all the dispensacaoMedicamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DispensacaoMedicamentosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DispensacaoMedicamentos");
        return dispensacaoMedicamentosRepository.findAll(pageable)
            .map(dispensacaoMedicamentosMapper::toDto);
    }


    /**
     * Get one dispensacaoMedicamentos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DispensacaoMedicamentosDTO> findOne(Long id) {
        log.debug("Request to get DispensacaoMedicamentos : {}", id);
        return dispensacaoMedicamentosRepository.findById(id)
            .map(dispensacaoMedicamentosMapper::toDto);
    }

    /**
     * Delete the dispensacaoMedicamentos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DispensacaoMedicamentos : {}", id);
        dispensacaoMedicamentosRepository.deleteById(id);
        dispensacaoMedicamentosSearchRepository.deleteById(id);
        dispensacaoMedicamentosRepository.deleteAll();
        dispensacaoMedicamentosSearchRepository.deleteAll();
    }

    /**
     * Search for the dispensacaoMedicamentos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DispensacaoMedicamentosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DispensacaoMedicamentos for query {}", query);
        return dispensacaoMedicamentosSearchRepository.search(queryStringQuery(query), pageable)
            .map(dispensacaoMedicamentosMapper::toDto);
    }
}
