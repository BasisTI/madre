package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Almoxarifado_;
import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado_;
import br.com.basis.suprimentos.domain.Fornecedor_;
import br.com.basis.suprimentos.domain.GrupoMaterial_;
import br.com.basis.suprimentos.domain.Material_;
import br.com.basis.suprimentos.domain.UnidadeMedida_;
import br.com.basis.suprimentos.domain.projection.Estoque;
import br.com.basis.suprimentos.domain.enumeration.CodigoTipoLancamento;
import br.com.basis.suprimentos.repository.EstoqueAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.EstoqueAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.ConsultaEstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.InclusaoSaldoEstoqueDTO;
import br.com.basis.suprimentos.service.dto.LancamentoDTO;
import br.com.basis.suprimentos.service.dto.TransacaoDTO;
import br.com.basis.suprimentos.service.mapper.EstoqueAlmoxarifadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
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
    public <S extends EstoqueAlmoxarifado> Page<Estoque> findAll(Pageable pageable, Example<S> example) {
        log.debug("Request to get all EstoqueAlmoxarifados");
        return estoqueAlmoxarifadoRepository.findBy(example, pageable, Estoque.class);
    }

    @Transactional(readOnly = true)
    public Page<Estoque> findAll(Pageable pageable) {
        log.debug("Request to get all EstoqueAlmoxarifados");
        return estoqueAlmoxarifadoRepository.findBy(pageable, Estoque.class);
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

    @Transactional(readOnly = true)
    public Page<Estoque> consultarEstoqueAlmoxarifado(Pageable pageable, ConsultaEstoqueAlmoxarifadoDTO consultaEstoqueAlmoxarifadoDTO) {
        Long fornecedorId = consultaEstoqueAlmoxarifadoDTO.getFornecedorId();
        Long almoxarifadoId = consultaEstoqueAlmoxarifadoDTO.getAlmoxarifadoId();
        Long grupoId = consultaEstoqueAlmoxarifadoDTO.getGrupoId();
        Long unidadeMedidaId = consultaEstoqueAlmoxarifadoDTO.getUnidadeMedidaId();
        Long materialId = consultaEstoqueAlmoxarifadoDTO.getMaterialId();
        Boolean ativo = consultaEstoqueAlmoxarifadoDTO.getAtivo();
        Boolean estocavel = consultaEstoqueAlmoxarifadoDTO.getEstocavel();

        Specification<EstoqueAlmoxarifado> spec = Specification.<EstoqueAlmoxarifado>where((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(EstoqueAlmoxarifado_.ativo), ativo)).and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(EstoqueAlmoxarifado_.estocavel), estocavel));

        if (Objects.nonNull(fornecedorId)) {
            spec = spec.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(EstoqueAlmoxarifado_.FORNECEDOR).get(Fornecedor_.ID), fornecedorId));
        }

        if (Objects.nonNull(almoxarifadoId)) {
            spec = spec.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(EstoqueAlmoxarifado_.ALMOXARIFADO).get(Almoxarifado_.ID), almoxarifadoId));
        }

        if (Objects.nonNull(materialId)) {
            spec = spec.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(EstoqueAlmoxarifado_.MATERIAL).get(Material_.ID), materialId));
        } else {
            if (Objects.nonNull(grupoId)) {
                spec = spec.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(EstoqueAlmoxarifado_.MATERIAL).get(Material_.GRUPO).get(GrupoMaterial_.ID), grupoId));
            }

            if (Objects.nonNull(unidadeMedidaId)) {
                spec = spec.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(EstoqueAlmoxarifado_.MATERIAL).get(Material_.UNIDADE_MEDIDA).get(UnidadeMedida_.ID), unidadeMedidaId));
            }
        }

        ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
        return estoqueAlmoxarifadoRepository.findAll(spec, pageable).map(estoque -> projectionFactory.createProjection(Estoque.class, estoque));
    }
}
