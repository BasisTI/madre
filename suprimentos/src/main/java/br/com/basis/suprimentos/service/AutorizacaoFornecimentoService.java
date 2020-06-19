package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.AutorizacaoFornecimento;
import br.com.basis.suprimentos.repository.AutorizacaoFornecimentoRepository;
import br.com.basis.suprimentos.repository.search.AutorizacaoFornecimentoSearchRepository;
import br.com.basis.suprimentos.service.dto.AutorizacaoFornecimentoDTO;
import br.com.basis.suprimentos.service.mapper.AutorizacaoFornecimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link AutorizacaoFornecimento}.
 */
@Service
@Transactional
public class AutorizacaoFornecimentoService {

    private final Logger log = LoggerFactory.getLogger(AutorizacaoFornecimentoService.class);

    private final AutorizacaoFornecimentoRepository autorizacaoFornecimentoRepository;

    private final AutorizacaoFornecimentoMapper autorizacaoFornecimentoMapper;

    private final AutorizacaoFornecimentoSearchRepository autorizacaoFornecimentoSearchRepository;

    public AutorizacaoFornecimentoService(AutorizacaoFornecimentoRepository autorizacaoFornecimentoRepository, AutorizacaoFornecimentoMapper autorizacaoFornecimentoMapper, AutorizacaoFornecimentoSearchRepository autorizacaoFornecimentoSearchRepository) {
        this.autorizacaoFornecimentoRepository = autorizacaoFornecimentoRepository;
        this.autorizacaoFornecimentoMapper = autorizacaoFornecimentoMapper;
        this.autorizacaoFornecimentoSearchRepository = autorizacaoFornecimentoSearchRepository;
    }

    /**
     * Save a autorizacaoFornecimento.
     *
     * @param autorizacaoFornecimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public AutorizacaoFornecimentoDTO save(AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO) {
        log.debug("Request to save AutorizacaoFornecimento : {}", autorizacaoFornecimentoDTO);
        AutorizacaoFornecimento autorizacaoFornecimento = autorizacaoFornecimentoMapper.toEntity(autorizacaoFornecimentoDTO);
        autorizacaoFornecimento = autorizacaoFornecimentoRepository.save(autorizacaoFornecimento);
        AutorizacaoFornecimentoDTO result = autorizacaoFornecimentoMapper.toDto(autorizacaoFornecimento);
        autorizacaoFornecimentoSearchRepository.save(autorizacaoFornecimento);
        return result;
    }

    /**
     * Get all the autorizacaoFornecimentos.
     *
     * @param pageable                   the pagination information.
     * @param autorizacaoFornecimentoDTO
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AutorizacaoFornecimentoDTO> findAll(Pageable pageable, AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO) {
        log.debug("Request to get all AutorizacaoFornecimentos");
        return autorizacaoFornecimentoRepository.findAll(
                Example.of(autorizacaoFornecimentoMapper.toEntity(autorizacaoFornecimentoDTO), ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING))
                , pageable)
                .map(autorizacaoFornecimentoMapper::toDto);
    }


    /**
     * Get one autorizacaoFornecimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutorizacaoFornecimentoDTO> findOne(Long id) {
        log.debug("Request to get AutorizacaoFornecimento : {}", id);
        return autorizacaoFornecimentoRepository.findById(id)
                .map(autorizacaoFornecimentoMapper::toDto);
    }

    /**
     * Delete the autorizacaoFornecimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutorizacaoFornecimento : {}", id);
        autorizacaoFornecimentoRepository.deleteById(id);
        autorizacaoFornecimentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the autorizacaoFornecimento corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AutorizacaoFornecimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AutorizacaoFornecimentos for query {}", query);
        return autorizacaoFornecimentoSearchRepository.search(queryStringQuery(query), pageable)
                .map(autorizacaoFornecimentoMapper::toDto);
    }
}
