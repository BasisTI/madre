package br.com.basis.madre.service;

import br.com.basis.madre.domain.Procedimento;
import br.com.basis.madre.repository.ProcedimentoRepository;
import br.com.basis.madre.repository.search.ProcedimentoSearchRepository;
import br.com.basis.madre.service.dto.ProcedimentoDTO;
import br.com.basis.madre.service.mapper.ProcedimentoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RequiredArgsConstructor
@Service
@Transactional
public class ProcedimentoService {

    private final Logger log = LoggerFactory.getLogger(ProcedimentoService.class);

    private final ProcedimentoRepository procedimentoRepository;

    private final ProcedimentoMapper procedimentoMapper;

    private final ProcedimentoSearchRepository procedimentoSearchRepository;

    /**
     * Save a procedimento.
     *
     * @param procedimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcedimentoDTO save(ProcedimentoDTO procedimentoDTO) {
        log.debug("Request to save Procedimento : {}", procedimentoDTO);
        Procedimento procedimento = procedimentoMapper.toEntity(procedimentoDTO);
        procedimento = procedimentoRepository.save(procedimento);
        ProcedimentoDTO result = procedimentoMapper.toDto(procedimento);
        procedimentoSearchRepository.save(procedimento);
        return result;
    }

    /**
     * Get all the procedimentos.
     *
     * @param procedimentoDTO
     * @param pageable        the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcedimentoDTO> findAll(
        ProcedimentoDTO procedimentoDTO, Pageable pageable) {
        log.debug("Request to get all Procedimentos");
        return procedimentoRepository.findAll(
            Example.of(procedimentoMapper.toEntity(procedimentoDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING))
            , pageable)
            .map(procedimentoMapper::toDto);
    }


    /**
     * Get one procedimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcedimentoDTO> findOne(Long id) {
        log.debug("Request to get Procedimento : {}", id);
        return procedimentoRepository.findById(id)
            .map(procedimentoMapper::toDto);
    }

    /**
     * Delete the procedimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Procedimento : {}", id);
        procedimentoRepository.deleteById(id);
        procedimentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the procedimento corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcedimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Procedimentos for query {}", query);
        return procedimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(procedimentoMapper::toDto);
    }

}
