package br.com.basis.madre.service;

import br.com.basis.madre.domain.OrigemDaInternacao;
import br.com.basis.madre.repository.OrigemDaInternacaoRepository;
import br.com.basis.madre.repository.search.OrigemDaInternacaoSearchRepository;
import br.com.basis.madre.service.dto.OrigemDaInternacaoDTO;
import br.com.basis.madre.service.mapper.OrigemDaInternacaoMapper;
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
public class OrigemDaInternacaoService {

    private final Logger log = LoggerFactory.getLogger(OrigemDaInternacaoService.class);

    private final OrigemDaInternacaoRepository origemDaInternacaoRepository;

    private final OrigemDaInternacaoMapper origemDaInternacaoMapper;

    private final OrigemDaInternacaoSearchRepository origemDaInternacaoSearchRepository;

    /**
     * Save a origemDaInternacao.
     *
     * @param origemDaInternacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public OrigemDaInternacaoDTO save(OrigemDaInternacaoDTO origemDaInternacaoDTO) {
        log.debug("Request to save OrigemDaInternacao : {}", origemDaInternacaoDTO);
        OrigemDaInternacao origemDaInternacao = origemDaInternacaoMapper
            .toEntity(origemDaInternacaoDTO);
        origemDaInternacao = origemDaInternacaoRepository.save(origemDaInternacao);
        OrigemDaInternacaoDTO result = origemDaInternacaoMapper.toDto(origemDaInternacao);
        origemDaInternacaoSearchRepository.save(origemDaInternacao);
        return result;
    }

    /**
     * Get all the origemDaInternacaos.
     *
     * @param origemDaInternacaoDTO
     * @param pageable              the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrigemDaInternacaoDTO> findAll(
        OrigemDaInternacaoDTO origemDaInternacaoDTO,
        Pageable pageable) {
        log.debug("Request to get all OrigemDaInternacaos");
        return origemDaInternacaoRepository.findAll(
            Example.of(origemDaInternacaoMapper.toEntity(origemDaInternacaoDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING))
            , pageable)
            .map(origemDaInternacaoMapper::toDto);
    }


    /**
     * Get one origemDaInternacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrigemDaInternacaoDTO> findOne(Long id) {
        log.debug("Request to get OrigemDaInternacao : {}", id);
        return origemDaInternacaoRepository.findById(id)
            .map(origemDaInternacaoMapper::toDto);
    }

    /**
     * Delete the origemDaInternacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrigemDaInternacao : {}", id);
        origemDaInternacaoRepository.deleteById(id);
        origemDaInternacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the origemDaInternacao corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrigemDaInternacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrigemDaInternacaos for query {}", query);
        return origemDaInternacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(origemDaInternacaoMapper::toDto);
    }

}
