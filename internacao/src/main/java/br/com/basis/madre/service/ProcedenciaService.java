package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.Procedencia;
import br.com.basis.madre.repository.ProcedenciaRepository;
import br.com.basis.madre.repository.search.ProcedenciaSearchRepository;
import br.com.basis.madre.service.dto.ProcedenciaDTO;
import br.com.basis.madre.service.mapper.ProcedenciaMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ProcedenciaService {

    private final Logger log = LoggerFactory.getLogger(ProcedenciaService.class);

    private final ProcedenciaRepository procedenciaRepository;

    private final ProcedenciaMapper procedenciaMapper;

    private final ProcedenciaSearchRepository procedenciaSearchRepository;

    /**
     * Save a procedencia.
     *
     * @param procedenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcedenciaDTO save(ProcedenciaDTO procedenciaDTO) {
        log.debug("Request to save Procedencia : {}", procedenciaDTO);
        Procedencia procedencia = procedenciaMapper.toEntity(procedenciaDTO);
        procedencia = procedenciaRepository.save(procedencia);
        ProcedenciaDTO result = procedenciaMapper.toDto(procedencia);
        procedenciaSearchRepository.save(procedencia);
        return result;
    }

    /**
     * Get all the procedencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcedenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Procedencias");
        return procedenciaRepository.findAll(pageable)
            .map(procedenciaMapper::toDto);
    }


    /**
     * Get one procedencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcedenciaDTO> findOne(Long id) {
        log.debug("Request to get Procedencia : {}", id);
        return procedenciaRepository.findById(id)
            .map(procedenciaMapper::toDto);
    }

    /**
     * Delete the procedencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Procedencia : {}", id);
        procedenciaRepository.deleteById(id);
        procedenciaSearchRepository.deleteById(id);
    }

    /**
     * Search for the procedencia corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcedenciaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Procedencias for query {}", query);
        return procedenciaSearchRepository.search(queryStringQuery(query), pageable)
            .map(procedenciaMapper::toDto);
    }

}
