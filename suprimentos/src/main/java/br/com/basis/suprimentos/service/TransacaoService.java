package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Transacao;
import br.com.basis.suprimentos.domain.enumeration.CodigoTipoTransacao;
import br.com.basis.suprimentos.repository.TransacaoRepository;
import br.com.basis.suprimentos.repository.search.TransacaoSearchRepository;
import br.com.basis.suprimentos.service.dto.InclusaoSaldoEstoqueDTO;
import br.com.basis.suprimentos.service.dto.TransacaoDTO;
import br.com.basis.suprimentos.service.mapper.TransacaoMapper;
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
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;
    private final TransacaoSearchRepository transacaoSearchRepository;

    public TransacaoDTO save(TransacaoDTO transacaoDTO) {
        log.debug("Request to save Transacao : {}", transacaoDTO);
        Transacao transacao = transacaoMapper.toEntity(transacaoDTO);
        transacao = transacaoRepository.save(transacao);
        TransacaoDTO result = transacaoMapper.toDto(transacao);
        transacaoSearchRepository.save(transacao);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<TransacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transacaos");
        return transacaoRepository.findAll(pageable)
            .map(transacaoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TransacaoDTO> findOne(Long id) {
        log.debug("Request to get Transacao : {}", id);
        return transacaoRepository.findById(id)
            .map(transacaoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TransacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Transacaos for query {}", query);
        return transacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(transacaoMapper::toDto);
    }

    public TransacaoDTO criarTransacao(InclusaoSaldoEstoqueDTO inclusaoSaldoEstoqueDTO) {
        TransacaoDTO transacao = new TransacaoDTO();
        transacao.setTipoTransacaoId(CodigoTipoTransacao.CREDITO.getCodigo());
        transacao.setQuantidadeItens((long) inclusaoSaldoEstoqueDTO.getQuantidade());
        transacao.setAlmoxarifadoId(inclusaoSaldoEstoqueDTO.getAlmoxarifadoId());
        transacao.setMaterialId(inclusaoSaldoEstoqueDTO.getMaterialId());

        return transacao;
    }
}
