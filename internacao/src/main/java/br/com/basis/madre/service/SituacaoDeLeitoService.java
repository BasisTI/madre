package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.SituacaoDeLeito;
import br.com.basis.madre.repository.SituacaoDeLeitoRepository;
import br.com.basis.madre.repository.search.SituacaoDeLeitoSearchRepository;
import br.com.basis.madre.service.dto.SituacaoDeLeitoDTO;
import br.com.basis.madre.service.mapper.SituacaoDeLeitoMapper;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
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
public class SituacaoDeLeitoService {

    private final Logger log = LoggerFactory.getLogger(SituacaoDeLeitoService.class);

    private final SituacaoDeLeitoRepository situacaoDeLeitoRepository;

    private final SituacaoDeLeitoMapper situacaoDeLeitoMapper;

    private final SituacaoDeLeitoSearchRepository situacaoDeLeitoSearchRepository;

    /**
     * Save a situacaoDeLeito.
     *
     * @param situacaoDeLeitoDTO the entity to save.
     * @return the persisted entity.
     */
    public SituacaoDeLeitoDTO save(SituacaoDeLeitoDTO situacaoDeLeitoDTO) {
        log.debug("Request to save SituacaoDeLeito : {}", situacaoDeLeitoDTO);
        SituacaoDeLeito situacaoDeLeito = situacaoDeLeitoMapper.toEntity(situacaoDeLeitoDTO);
        situacaoDeLeito = situacaoDeLeitoRepository.save(situacaoDeLeito);
        SituacaoDeLeitoDTO result = situacaoDeLeitoMapper.toDto(situacaoDeLeito);
        situacaoDeLeitoSearchRepository.save(situacaoDeLeito);
        return result;
    }

    /**
     * Get all the situacaoDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SituacaoDeLeitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SituacaoDeLeitos");
        return situacaoDeLeitoRepository.findAll(pageable)
            .map(situacaoDeLeitoMapper::toDto);
    }


    /**
     * Get one situacaoDeLeito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SituacaoDeLeitoDTO> findOne(Long id) {
        log.debug("Request to get SituacaoDeLeito : {}", id);
        return situacaoDeLeitoRepository.findById(id)
            .map(situacaoDeLeitoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public SituacaoDeLeitoDTO findByNomeIgnoreCase(String nome) {
        return
            situacaoDeLeitoMapper
                .toDto(situacaoDeLeitoRepository.findByNomeIgnoreCase(nome).orElseThrow(
                    EntityNotFoundException::new));
    }

    /**
     * Delete the situacaoDeLeito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SituacaoDeLeito : {}", id);
        situacaoDeLeitoRepository.deleteById(id);
        situacaoDeLeitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the situacaoDeLeito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SituacaoDeLeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SituacaoDeLeitos for query {}", query);
        return situacaoDeLeitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(situacaoDeLeitoMapper::toDto);
    }
}
