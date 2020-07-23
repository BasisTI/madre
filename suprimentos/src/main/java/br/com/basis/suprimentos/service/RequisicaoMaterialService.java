package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import br.com.basis.suprimentos.domain.Lote;
import br.com.basis.suprimentos.domain.RequisicaoMaterial;
import br.com.basis.suprimentos.domain.enumeration.CodigoSituacaoRequisicaoMaterial;
import br.com.basis.suprimentos.domain.projection.RequisicaoMaterialResumo;
import br.com.basis.suprimentos.repository.RequisicaoMaterialRepository;
import br.com.basis.suprimentos.repository.search.RequisicaoMaterialSearchRepository;
import br.com.basis.suprimentos.service.dto.AlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.ItemRequisicaoDTO;
import br.com.basis.suprimentos.service.dto.MaterialDTO;
import br.com.basis.suprimentos.service.dto.RequisicaoMaterialDTO;
import br.com.basis.suprimentos.service.mapper.LoteMapper;
import br.com.basis.suprimentos.service.mapper.RequisicaoMaterialMapper;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class RequisicaoMaterialService {
    private final RequisicaoMaterialRepository requisicaoMaterialRepository;
    private final RequisicaoMaterialMapper requisicaoMaterialMapper;
    private final RequisicaoMaterialSearchRepository requisicaoMaterialSearchRepository;
    private final AuthenticationPrincipalService authenticationPrincipalService;
    private final EstoqueAlmoxarifadoService estoqueAlmoxarifadoService;
    private final LoteService loteService;
    private final LoteMapper loteMapper;

    public RequisicaoMaterialDTO efetivarRequisicao(RequisicaoMaterialDTO requisicaoMaterialDTO) {
        requisicaoMaterialDTO.setConfirmadoEm(ZonedDateTime.now(ZoneId.systemDefault()));
        requisicaoMaterialDTO.setConfirmadoPor(authenticationPrincipalService.getLoginAtivo());
        requisicaoMaterialDTO.setSituacaoId(CodigoSituacaoRequisicaoMaterial.CONFIRMADA.getCodigo());
        requisicaoMaterialDTO.getItens().stream().forEach(item -> {
            dispensarMaterial(requisicaoMaterialDTO, item);
        });

        return save(requisicaoMaterialDTO);
    }

    private void dispensarMaterial(RequisicaoMaterialDTO requisicaoMaterialDTO, ItemRequisicaoDTO item) {
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoService.recuperaEstoquePorAlmoxarifadoIdEMaterialId(requisicaoMaterialDTO.getAlmoxarifadoId(), item.getMaterialId());
        EstoqueAlmoxarifado estoque = estoqueAlmoxarifadoService.findOneEntity(estoqueAlmoxarifadoDTO.getId());
        Lote lote = estoque.getLotes().stream().filter(l -> l.getQuantidadeDisponivel() >= item.getQuantidade()).findFirst().orElseThrow(EntityNotFoundException::new);
        lote.setQuantidadeDisponivel(lote.getQuantidadeDisponivel() - item.getQuantidade());
        loteService.save(loteMapper.toDto(lote));
    }

    public RequisicaoMaterialDTO criarRequisicaoMaterial(RequisicaoMaterialDTO requisicaoMaterialDTO) {
        requisicaoMaterialDTO.setGeradoEm(ZonedDateTime.now(ZoneId.systemDefault()));
        requisicaoMaterialDTO.setGeradoPor(authenticationPrincipalService.getLoginAtivo());
        requisicaoMaterialDTO.setSituacaoId(CodigoSituacaoRequisicaoMaterial.GERADA.getCodigo());
        return save(requisicaoMaterialDTO);
    }

    public RequisicaoMaterialDTO save(RequisicaoMaterialDTO requisicaoMaterialDTO) {
        log.debug("Request to save RequisicaoMaterial : {}", requisicaoMaterialDTO);
        final RequisicaoMaterial requisicaoMaterial = requisicaoMaterialMapper.toEntity(requisicaoMaterialDTO);
        requisicaoMaterial.getItens().forEach(item -> {
            item.setRequisicao(requisicaoMaterial);
        });
        final RequisicaoMaterial saved = requisicaoMaterialRepository.save(requisicaoMaterial);
        RequisicaoMaterialDTO result = requisicaoMaterialMapper.toDto(saved);
        requisicaoMaterialSearchRepository.save(saved);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoMaterialResumo> findAllRequisicoesMaterialResumo(Pageable pageable) {
        return requisicaoMaterialRepository.findBy(RequisicaoMaterialResumo.class, pageable);
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoMaterialResumo> findAllRequisicoesMaterialResumoNaoEfetivadas(Pageable pageable) {
        return requisicaoMaterialRepository.findByConfirmadoEmIsNull(RequisicaoMaterialResumo.class, pageable);
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoMaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequisicaoMaterials");
        return requisicaoMaterialRepository.findAll(pageable)
            .map(requisicaoMaterialMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<RequisicaoMaterialDTO> findOne(Long id) {
        log.debug("Request to get RequisicaoMaterial : {}", id);
        return requisicaoMaterialRepository.findById(id)
            .map(requisicaoMaterialMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete RequisicaoMaterial : {}", id);
        requisicaoMaterialRepository.deleteById(id);
        requisicaoMaterialSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoMaterialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequisicaoMaterials for query {}", query);
        return requisicaoMaterialSearchRepository.search(queryStringQuery(query), pageable)
            .map(requisicaoMaterialMapper::toDto);
    }
}
