package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.TipoLancamento;
import br.com.basis.suprimentos.repository.TipoLancamentoRepository;
import br.com.basis.suprimentos.repository.search.TipoLancamentoSearchRepository;
import br.com.basis.suprimentos.service.dto.TipoLancamentoDTO;
import br.com.basis.suprimentos.service.mapper.TipoLancamentoMapper;
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
public class TipoLancamentoService {
    private final TipoLancamentoRepository tipoLancamentoRepository;
    private final TipoLancamentoMapper tipoLancamentoMapper;
    private final TipoLancamentoSearchRepository tipoLancamentoSearchRepository;

    public TipoLancamentoDTO save(TipoLancamentoDTO tipoLancamentoDTO) {
        log.debug("Request to save TipoLancamento : {}", tipoLancamentoDTO);
        TipoLancamento tipoLancamento = tipoLancamentoMapper.toEntity(tipoLancamentoDTO);
        tipoLancamento = tipoLancamentoRepository.save(tipoLancamento);
        TipoLancamentoDTO result = tipoLancamentoMapper.toDto(tipoLancamento);
        tipoLancamentoSearchRepository.save(tipoLancamento);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<TipoLancamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoLancamentos");
        return tipoLancamentoRepository.findAll(pageable)
            .map(tipoLancamentoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TipoLancamentoDTO> findOne(Long id) {
        log.debug("Request to get TipoLancamento : {}", id);
        return tipoLancamentoRepository.findById(id)
            .map(tipoLancamentoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete TipoLancamento : {}", id);
        tipoLancamentoRepository.deleteById(id);
        tipoLancamentoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<TipoLancamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoLancamentos for query {}", query);
        return tipoLancamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoLancamentoMapper::toDto);
    }
}
