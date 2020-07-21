package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.TipoTransacao;
import br.com.basis.suprimentos.repository.TipoTransacaoRepository;
import br.com.basis.suprimentos.repository.search.TipoTransacaoSearchRepository;
import br.com.basis.suprimentos.service.dto.TipoTransacaoDTO;
import br.com.basis.suprimentos.service.mapper.TipoTransacaoMapper;
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
public class TipoTransacaoService {
    private final TipoTransacaoRepository tipoTransacaoRepository;
    private final TipoTransacaoMapper tipoTransacaoMapper;
    private final TipoTransacaoSearchRepository tipoTransacaoSearchRepository;

    public TipoTransacaoDTO save(TipoTransacaoDTO tipoTransacaoDTO) {
        log.debug("Request to save TipoTransacao : {}", tipoTransacaoDTO);
        TipoTransacao tipoTransacao = tipoTransacaoMapper.toEntity(tipoTransacaoDTO);
        tipoTransacao = tipoTransacaoRepository.save(tipoTransacao);
        TipoTransacaoDTO result = tipoTransacaoMapper.toDto(tipoTransacao);
        tipoTransacaoSearchRepository.save(tipoTransacao);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<TipoTransacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoTransacaos");
        return tipoTransacaoRepository.findAll(pageable)
            .map(tipoTransacaoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TipoTransacaoDTO> findOne(Long id) {
        log.debug("Request to get TipoTransacao : {}", id);
        return tipoTransacaoRepository.findById(id)
            .map(tipoTransacaoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete TipoTransacao : {}", id);
        tipoTransacaoRepository.deleteById(id);
        tipoTransacaoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<TipoTransacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoTransacaos for query {}", query);
        return tipoTransacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoTransacaoMapper::toDto);
    }
}
