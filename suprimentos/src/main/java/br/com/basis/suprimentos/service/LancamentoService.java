package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Lancamento;
import br.com.basis.suprimentos.repository.LancamentoRepository;
import br.com.basis.suprimentos.repository.search.LancamentoSearchRepository;
import br.com.basis.suprimentos.service.dto.LancamentoDTO;
import br.com.basis.suprimentos.service.mapper.LancamentoMapper;
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
public class LancamentoService {
    private final LancamentoRepository lancamentoRepository;
    private final LancamentoMapper lancamentoMapper;
    private final LancamentoSearchRepository lancamentoSearchRepository;

    public LancamentoDTO save(LancamentoDTO lancamentoDTO) {
        log.debug("Request to save Lancamento : {}", lancamentoDTO);
        Lancamento lancamento = lancamentoMapper.toEntity(lancamentoDTO);
        lancamento = lancamentoRepository.save(lancamento);
        LancamentoDTO result = lancamentoMapper.toDto(lancamento);
        lancamentoSearchRepository.save(lancamento);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lancamentos");
        return lancamentoRepository.findAll(pageable)
            .map(lancamentoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<LancamentoDTO> findOne(Long id) {
        log.debug("Request to get Lancamento : {}", id);
        return lancamentoRepository.findById(id)
            .map(lancamentoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Lancamento : {}", id);
        lancamentoRepository.deleteById(id);
        lancamentoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<LancamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Lancamentos for query {}", query);
        return lancamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(lancamentoMapper::toDto);
    }
}
