package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import br.com.basis.suprimentos.domain.Lote;
import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import br.com.basis.suprimentos.domain.projection.TransferenciaAutomatica;
import br.com.basis.suprimentos.repository.TransferenciaAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.TransferenciaAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.ItemTransferenciaDTO;
import br.com.basis.suprimentos.service.dto.TransferenciaAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.LoteMapper;
import br.com.basis.suprimentos.service.mapper.TransferenciaAlmoxarifadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TransferenciaAlmoxarifadoService {
    private final TransferenciaAlmoxarifadoRepository transferenciaAlmoxarifadoRepository;
    private final TransferenciaAlmoxarifadoMapper transferenciaAlmoxarifadoMapper;
    private final LoteMapper loteMapper;
    private final TransferenciaAlmoxarifadoSearchRepository transferenciaAlmoxarifadoSearchRepository;
    private final AuthenticationPrincipalService authenticationPrincipalService;
    private final EstoqueAlmoxarifadoService estoqueAlmoxarifadoService;
    private final LoteService loteService;

    public TransferenciaAlmoxarifadoDTO criarNovaTransferenciaAutomatica(TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) {
        transferenciaAlmoxarifadoDTO.getInformacaoTransferencia().setAtiva(true);
        transferenciaAlmoxarifadoDTO.getInformacaoTransferencia().setEfetivada(false);
        transferenciaAlmoxarifadoDTO.setGeradoEm(ZonedDateTime.now(ZoneId.systemDefault()));
        transferenciaAlmoxarifadoDTO.setGeradoPor(authenticationPrincipalService.getLoginAtivo());
        return save(transferenciaAlmoxarifadoDTO);
    }

    public TransferenciaAlmoxarifadoDTO save(TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) {
        log.debug("Request to save TransferenciaAlmoxarifado : {}", transferenciaAlmoxarifadoDTO);
        final TransferenciaAlmoxarifado transferenciaAlmoxarifado = transferenciaAlmoxarifadoMapper.toEntity(transferenciaAlmoxarifadoDTO);
        transferenciaAlmoxarifado.getItens().forEach(item -> {
            item.setTransferenciaAlmoxarifado(transferenciaAlmoxarifado);
        });
        TransferenciaAlmoxarifado saved = transferenciaAlmoxarifadoRepository.save(transferenciaAlmoxarifado);
        TransferenciaAlmoxarifadoDTO result = transferenciaAlmoxarifadoMapper.toDto(saved);
        transferenciaAlmoxarifadoSearchRepository.save(saved);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaAutomatica> findAllTransferenciasAutomaticas(Pageable pageable) {
        log.debug("Request to get all TransferenciaAlmoxarifados");
        return transferenciaAlmoxarifadoRepository.findBy(TransferenciaAutomatica.class, pageable);
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaAutomatica> findAllTransferenciasAutomaticasNaoEvetivadas(Pageable pageable) {
        log.debug("Request to get all TransferenciaAlmoxarifados");
        return transferenciaAlmoxarifadoRepository.findByInformacaoTransferenciaEfetivada(false, TransferenciaAutomatica.class, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<TransferenciaAlmoxarifadoDTO> findOne(Long id) {
        log.debug("Request to get TransferenciaAlmoxarifado : {}", id);
        return transferenciaAlmoxarifadoRepository.findById(id)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public <T> Optional<T> findOne(Long id, Class<T> projectionClass) {
        log.debug("Request to get TransferenciaAutomatica : {}", id);
        return transferenciaAlmoxarifadoRepository.findOneById(id, projectionClass);
    }

    public void delete(Long id) {
        log.debug("Request to delete TransferenciaAlmoxarifado : {}", id);
        transferenciaAlmoxarifadoRepository.deleteById(id);
        transferenciaAlmoxarifadoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaAlmoxarifadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TransferenciaAlmoxarifados for query {}", query);
        return transferenciaAlmoxarifadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }

    public void transferirItensEntreLotes(EstoqueAlmoxarifadoDTO estoqueOrigem, EstoqueAlmoxarifadoDTO estoqueDestino, Long materialId, Integer quantidadeEnviada) {
        EstoqueAlmoxarifado origem = estoqueAlmoxarifadoService.findOneEntity(estoqueOrigem.getId());
        EstoqueAlmoxarifado destino = estoqueAlmoxarifadoService.findOneEntity(estoqueDestino.getId());

        Lote loteOrigem = origem.getLotes().stream().findFirst().orElseThrow(EntityNotFoundException::new);
        loteOrigem.setQuantidadeDisponivel(loteOrigem.getQuantidadeDisponivel() - quantidadeEnviada);

        Lote loteDestino = destino.getLotes().stream().findFirst().orElseThrow(EntityNotFoundException::new);
        loteDestino.setQuantidadeDisponivel(loteDestino.getQuantidadeDisponivel() + quantidadeEnviada);

        loteService.save(loteMapper.toDto(loteOrigem));
        loteService.save(loteMapper.toDto(loteDestino));
    }

    public void transferirItensEntreAlmoxarifados(Long origemId, Long destinoId, Set<ItemTransferenciaDTO> itens) {
        itens.stream().forEach(i -> {
            Integer quantidadeEnviada = i.getQuantidadeEnviada();
            Long materialId = i.getMaterialId();

            transferirItensEntreLotes(
                estoqueAlmoxarifadoService.recuperaEstoquePorAlmoxarifadoIdEMaterialId(origemId, materialId),
                estoqueAlmoxarifadoService.recuperaEstoquePorAlmoxarifadoIdEMaterialId(destinoId, materialId),
                materialId,
                quantidadeEnviada
            );
        });
    }

    public void efetivarTransferenciaAutomaticaPorId(Long id) {
        TransferenciaAlmoxarifadoDTO dto = findOne(id).orElseThrow(EntityNotFoundException::new);

        if (dto.getInformacaoTransferencia().getEfetivada()) {
            return;
        }

        dto.getInformacaoTransferencia().getEfetivada();
        dto.getInformacaoTransferencia().setEfetivada(true);
        transferirItensEntreAlmoxarifados(dto.getOrigemId(), dto.getDestinoId(), dto.getItens());
    }
}
