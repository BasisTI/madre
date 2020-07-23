package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import br.com.basis.suprimentos.domain.enumeration.CodigoTipoLancamento;
import br.com.basis.suprimentos.repository.EstoqueAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.EstoqueAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.InclusaoSaldoEstoqueDTO;
import br.com.basis.suprimentos.service.dto.LancamentoDTO;
import br.com.basis.suprimentos.service.dto.TransacaoDTO;
import br.com.basis.suprimentos.service.mapper.EstoqueAlmoxarifadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class EstoqueAlmoxarifadoService {
    private final EstoqueAlmoxarifadoRepository estoqueAlmoxarifadoRepository;
    private final EstoqueAlmoxarifadoMapper estoqueAlmoxarifadoMapper;
    private final EstoqueAlmoxarifadoSearchRepository estoqueAlmoxarifadoSearchRepository;
    private final LancamentoService lancamentoService;
    private final TransacaoService transacaoService;
    private final LoteService loteService;

    public EstoqueAlmoxarifadoDTO save(EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO) {
        log.debug("Request to save EstoqueAlmoxarifado : {}", estoqueAlmoxarifadoDTO);
        EstoqueAlmoxarifado estoqueAlmoxarifado = estoqueAlmoxarifadoMapper.toEntity(estoqueAlmoxarifadoDTO);
        estoqueAlmoxarifado = estoqueAlmoxarifadoRepository.save(estoqueAlmoxarifado);
        EstoqueAlmoxarifadoDTO result = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);
        estoqueAlmoxarifadoSearchRepository.save(estoqueAlmoxarifado);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<EstoqueAlmoxarifadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstoqueAlmoxarifados");
        return estoqueAlmoxarifadoRepository.findAll(pageable)
            .map(estoqueAlmoxarifadoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<EstoqueAlmoxarifadoDTO> findOne(Long id) {
        log.debug("Request to get EstoqueAlmoxarifado : {}", id);
        return estoqueAlmoxarifadoRepository.findById(id)
            .map(estoqueAlmoxarifadoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public EstoqueAlmoxarifado findOneEntity(Long id) {
        return estoqueAlmoxarifadoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(Long id) {
        log.debug("Request to delete EstoqueAlmoxarifado : {}", id);
        estoqueAlmoxarifadoRepository.deleteById(id);
        estoqueAlmoxarifadoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<EstoqueAlmoxarifadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EstoqueAlmoxarifados for query {}", query);
        return estoqueAlmoxarifadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(estoqueAlmoxarifadoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public EstoqueAlmoxarifadoDTO recuperaEstoquePorAlmoxarifadoIdEMaterialId(Long almoxarifadoId, Long materialId) {
        return estoqueAlmoxarifadoRepository.findByAlmoxarifadoIdAndMaterialId(almoxarifadoId, materialId).map(estoqueAlmoxarifadoMapper::toDto).orElseThrow(EntityNotFoundException::new);
    }

    public void incluirSaldoEstoque(InclusaoSaldoEstoqueDTO inclusaoSaldoEstoqueDTO) {
        LancamentoDTO lancamentoDTO = lancamentoService.criarLancamento(CodigoTipoLancamento.INCLUSAO_SALDO);
        TransacaoDTO transacaoDTO = transacaoService.criarTransacao(inclusaoSaldoEstoqueDTO);

        // TODO: Incluir saldo no estoque

        lancamentoDTO = lancamentoService.save(lancamentoDTO);
        transacaoDTO.setLancamentoId(lancamentoDTO.getId());
        transacaoService.save(transacaoDTO);
    }
}
