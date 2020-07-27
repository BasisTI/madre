package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.EstoqueGeral;
import br.com.basis.suprimentos.domain.enumeration.CodigoTipoLancamento;
import br.com.basis.suprimentos.repository.EstoqueGeralRepository;
import br.com.basis.suprimentos.repository.search.EstoqueGeralSearchRepository;
import br.com.basis.suprimentos.service.dto.EstoqueGeralDTO;
import br.com.basis.suprimentos.service.dto.InclusaoSaldoEstoqueDTO;
import br.com.basis.suprimentos.service.dto.LancamentoDTO;
import br.com.basis.suprimentos.service.dto.TransacaoDTO;
import br.com.basis.suprimentos.service.mapper.EstoqueGeralMapper;
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
public class EstoqueGeralService {
    private final EstoqueGeralRepository estoqueGeralRepository;
    private final EstoqueGeralMapper estoqueGeralMapper;
    private final EstoqueGeralSearchRepository estoqueGeralSearchRepository;
    private final LancamentoService lancamentoService;
    private final TransacaoService transacaoService;

    public EstoqueGeralDTO incluirSaldoEstoque(InclusaoSaldoEstoqueDTO inclusaoSaldoEstoqueDTO) {
        LancamentoDTO lancamentoDTO = lancamentoService.criarLancamento(CodigoTipoLancamento.INCLUSAO_SALDO);
        TransacaoDTO transacaoDTO = transacaoService.criarTransacao(inclusaoSaldoEstoqueDTO);

        EstoqueGeralDTO estoqueGeralDTO = new EstoqueGeralDTO();
        estoqueGeralDTO.setMaterialId(inclusaoSaldoEstoqueDTO.getMaterialId());
        estoqueGeralDTO.setQuantidade(Long.valueOf(inclusaoSaldoEstoqueDTO.getQuantidade()));
        estoqueGeralDTO.setValor(inclusaoSaldoEstoqueDTO.getValorTotal());

        estoqueGeralDTO = save(estoqueGeralDTO);

        lancamentoDTO = lancamentoService.save(lancamentoDTO);
        transacaoDTO.setLancamentoId(lancamentoDTO.getId());
        transacaoService.save(transacaoDTO);

        return estoqueGeralDTO;
    }

    public EstoqueGeralDTO save(EstoqueGeralDTO estoqueGeralDTO) {
        log.debug("Request to save EstoqueGeral : {}", estoqueGeralDTO);
        EstoqueGeral estoqueGeral = estoqueGeralMapper.toEntity(estoqueGeralDTO);
        estoqueGeral = estoqueGeralRepository.save(estoqueGeral);
        EstoqueGeralDTO result = estoqueGeralMapper.toDto(estoqueGeral);
        estoqueGeralSearchRepository.save(estoqueGeral);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<EstoqueGeralDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstoqueGerals");
        return estoqueGeralRepository.findAll(pageable)
            .map(estoqueGeralMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<EstoqueGeralDTO> findOne(Long id) {
        log.debug("Request to get EstoqueGeral : {}", id);
        return estoqueGeralRepository.findById(id)
            .map(estoqueGeralMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete EstoqueGeral : {}", id);
        estoqueGeralRepository.deleteById(id);
        estoqueGeralSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<EstoqueGeralDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EstoqueGerals for query {}", query);
        return estoqueGeralSearchRepository.search(queryStringQuery(query), pageable)
            .map(estoqueGeralMapper::toDto);
    }
}
