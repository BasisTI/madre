package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Composicao;
import br.com.basis.suprimentos.repository.ComposicaoRepository;
import br.com.basis.suprimentos.repository.search.ComposicaoSearchRepository;
import br.com.basis.suprimentos.service.dto.ComposicaoDTO;
import br.com.basis.suprimentos.service.mapper.ComposicaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ComposicaoService {

    private final ComposicaoRepository composicaoRepository;

    private final ComposicaoMapper composicaoMapper;

    private final ComposicaoSearchRepository composicaoSearchRepository;

    public ComposicaoDTO save(ComposicaoDTO composicaoDTO) {
        log.debug("Request to save Composicao : {}", composicaoDTO);
        Composicao composicao = composicaoMapper.toEntity(composicaoDTO);
        composicao = composicaoRepository.save(composicao);
        ComposicaoDTO result = composicaoMapper.toDto(composicao);
        composicaoSearchRepository.save(composicao);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<ComposicaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Composicaos");
        return composicaoRepository.findAll(pageable)
            .map(composicaoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ComposicaoDTO> findOne(Long id) {
        log.debug("Request to get Composicao : {}", id);
        return composicaoRepository.findById(id)
            .map(composicaoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Composicao : {}", id);
        composicaoRepository.deleteById(id);
        composicaoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ComposicaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Composicaos for query {}", query);
        return composicaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(composicaoMapper::toDto);
    }
}
