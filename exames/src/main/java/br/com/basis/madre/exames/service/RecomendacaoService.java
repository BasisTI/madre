package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.Recomendacao;
import br.com.basis.madre.exames.repository.RecomendacaoRepository;
import br.com.basis.madre.exames.service.dto.RecomendacaoDTO;
import br.com.basis.madre.exames.service.mapper.RecomendacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Recomendacao}.
 */
@Service
@Transactional
public class RecomendacaoService {

    private final Logger log = LoggerFactory.getLogger(RecomendacaoService.class);

    private final RecomendacaoRepository recomendacaoRepository;

    private final RecomendacaoMapper recomendacaoMapper;

    public RecomendacaoService(RecomendacaoRepository recomendacaoRepository, RecomendacaoMapper recomendacaoMapper) {
        this.recomendacaoRepository = recomendacaoRepository;
        this.recomendacaoMapper = recomendacaoMapper;
    }

    /**
     * Save a recomendacao.
     *
     * @param recomendacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public RecomendacaoDTO save(RecomendacaoDTO recomendacaoDTO) {
        log.debug("Request to save Recomendacao : {}", recomendacaoDTO);
        Recomendacao recomendacao = recomendacaoMapper.toEntity(recomendacaoDTO);
        recomendacao = recomendacaoRepository.save(recomendacao);
        return recomendacaoMapper.toDto(recomendacao);
    }

    /**
     * Get all the recomendacaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RecomendacaoDTO> findAll() {
        log.debug("Request to get all Recomendacaos");
        return recomendacaoRepository.findAll().stream()
            .map(recomendacaoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one recomendacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecomendacaoDTO> findOne(Long id) {
        log.debug("Request to get Recomendacao : {}", id);
        return recomendacaoRepository.findById(id)
            .map(recomendacaoMapper::toDto);
    }

    /**
     * Delete the recomendacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recomendacao : {}", id);
        recomendacaoRepository.deleteById(id);
    }

    /**
     * Search for the recomendacao corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RecomendacaoDTO> search(String query) {
        log.debug("Request to search Recomendacaos for query {}", query);
            return recomendacaoRepository.search(Recomendacao.PREFIX, query).stream()
            .map(recomendacaoMapper::toDto)
        .collect(Collectors.toList());
    }
}
