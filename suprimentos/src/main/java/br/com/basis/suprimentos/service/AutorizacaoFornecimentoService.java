package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.AutorizacaoFornecimento;
import br.com.basis.suprimentos.repository.AutorizacaoFornecimentoRepository;
import br.com.basis.suprimentos.repository.search.AutorizacaoFornecimentoSearchRepository;
import br.com.basis.suprimentos.service.dto.AutorizacaoFornecimentoDTO;
import br.com.basis.suprimentos.service.mapper.AutorizacaoFornecimentoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
public class AutorizacaoFornecimentoService {
    private final AutorizacaoFornecimentoRepository autorizacaoFornecimentoRepository;
    private final AutorizacaoFornecimentoMapper autorizacaoFornecimentoMapper;
    private final AutorizacaoFornecimentoSearchRepository autorizacaoFornecimentoSearchRepository;

    public AutorizacaoFornecimentoDTO save(AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO) {
        log.debug("Request to save AutorizacaoFornecimento : {}", autorizacaoFornecimentoDTO);
        AutorizacaoFornecimento autorizacaoFornecimento = autorizacaoFornecimentoMapper.toEntity(autorizacaoFornecimentoDTO);
        autorizacaoFornecimento = autorizacaoFornecimentoRepository.save(autorizacaoFornecimento);
        AutorizacaoFornecimentoDTO result = autorizacaoFornecimentoMapper.toDto(autorizacaoFornecimento);
        autorizacaoFornecimentoSearchRepository.save(autorizacaoFornecimento);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<AutorizacaoFornecimentoDTO> findAll(Pageable pageable, AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO) {
        log.debug("Request to get all AutorizacaoFornecimentos");
        return autorizacaoFornecimentoRepository.findAll(
            Example.of(autorizacaoFornecimentoMapper.toEntity(autorizacaoFornecimentoDTO), ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING))
            , pageable)
            .map(autorizacaoFornecimentoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<AutorizacaoFornecimentoDTO> findOne(Long id) {
        log.debug("Request to get AutorizacaoFornecimento : {}", id);
        return autorizacaoFornecimentoRepository.findById(id)
            .map(autorizacaoFornecimentoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete AutorizacaoFornecimento : {}", id);
        autorizacaoFornecimentoRepository.deleteById(id);
        autorizacaoFornecimentoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<AutorizacaoFornecimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AutorizacaoFornecimentos for query {}", query);
        return autorizacaoFornecimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(autorizacaoFornecimentoMapper::toDto);
    }
}
