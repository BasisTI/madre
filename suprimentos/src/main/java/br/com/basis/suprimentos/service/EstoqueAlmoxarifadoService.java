package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Almoxarifado_;
import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado_;
import br.com.basis.suprimentos.domain.Fornecedor_;
import br.com.basis.suprimentos.domain.GrupoMaterial_;
import br.com.basis.suprimentos.domain.Lote_;
import br.com.basis.suprimentos.domain.Material_;
import br.com.basis.suprimentos.domain.UnidadeMedida_;
import br.com.basis.suprimentos.domain.projection.Estoque;
import br.com.basis.suprimentos.repository.EstoqueAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.EstoqueAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.ConsultaEstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.SaldoEstoqueAlmoxarifado;
import br.com.basis.suprimentos.service.mapper.EstoqueAlmoxarifadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
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
    private final EntityManager entityManager;

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
    public Page<EstoqueAlmoxarifadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstoqueAlmoxarifados");
        return estoqueAlmoxarifadoRepository.findAll(pageable).map(estoqueAlmoxarifadoMapper::toDto);
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
    public Page<SaldoEstoqueAlmoxarifado> obterSaldoTodosEstoques(ConsultaEstoqueAlmoxarifadoDTO consultaEstoqueAlmoxarifadoDTO, Pageable pageable) {
        Long fornecedorId = consultaEstoqueAlmoxarifadoDTO.getFornecedorId();
        Long almoxarifadoId = consultaEstoqueAlmoxarifadoDTO.getAlmoxarifadoId();
        Long grupoId = consultaEstoqueAlmoxarifadoDTO.getGrupoId();
        Long unidadeMedidaId = consultaEstoqueAlmoxarifadoDTO.getUnidadeMedidaId();
        Long materialId = consultaEstoqueAlmoxarifadoDTO.getMaterialId();
        Boolean ativo = consultaEstoqueAlmoxarifadoDTO.getAtivo();
        Boolean estocavel = consultaEstoqueAlmoxarifadoDTO.getEstocavel();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SaldoEstoqueAlmoxarifado> query = cb.createQuery(SaldoEstoqueAlmoxarifado.class);
        Root<EstoqueAlmoxarifado> estoqueAlmoxarifadoRoot = query.from(EstoqueAlmoxarifado.class);

        CompoundSelection<SaldoEstoqueAlmoxarifado> construct = cb.construct(
            SaldoEstoqueAlmoxarifado.class,
            estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.ID),
            estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.ATIVO),
            estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.ESTOCAVEL),
            cb.sum(estoqueAlmoxarifadoRoot.join(EstoqueAlmoxarifado_.LOTES).get(Lote_.QUANTIDADE_DISPONIVEL)),
            cb.sum(estoqueAlmoxarifadoRoot.join(EstoqueAlmoxarifado_.LOTES).get(Lote_.QUANTIDADE_BLOQUEADA)),
            cb.sum(estoqueAlmoxarifadoRoot.join(EstoqueAlmoxarifado_.LOTES).get(Lote_.QUANTIDADE_PROBLEMA)),
            estoqueAlmoxarifadoRoot.join(EstoqueAlmoxarifado_.MATERIAL).get(Material_.NOME).alias("nomeMaterial"),
            estoqueAlmoxarifadoRoot.join(EstoqueAlmoxarifado_.FORNECEDOR).get(Fornecedor_.NOME_FANTASIA).alias("nomeFantasia"),
            estoqueAlmoxarifadoRoot.join(EstoqueAlmoxarifado_.ALMOXARIFADO).get(Almoxarifado_.DESCRICAO).alias("nomeAlmoxarifado")
        );

        query.select(construct);
        query.groupBy(
            estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.ID),
            estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.MATERIAL).get(Material_.NOME),
            estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.FORNECEDOR).get(Fornecedor_.NOME_FANTASIA),
            estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.ALMOXARIFADO).get(Almoxarifado_.DESCRICAO)
        );
        query.where(cb.equal(estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.ATIVO), ativo));
        query.where(cb.equal(estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.ESTOCAVEL), estocavel));

        if (fornecedorId != null) {
            query.where(cb.equal(estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.FORNECEDOR).get(Fornecedor_.ID), fornecedorId));
        }

        if (almoxarifadoId != null) {
            query.where(cb.equal(estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.ALMOXARIFADO).get(Almoxarifado_.ID), almoxarifadoId));
        }

        if (materialId != null) {
            query.where(cb.equal(estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.MATERIAL).get(Material_.ID), materialId));
        } else {
            if (Objects.nonNull(grupoId)) {
                query.where(cb.equal(estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.MATERIAL).get(Material_.GRUPO).get(GrupoMaterial_.ID), grupoId));
            }

            if (Objects.nonNull(unidadeMedidaId)) {
                query.where(cb.equal(estoqueAlmoxarifadoRoot.get(EstoqueAlmoxarifado_.MATERIAL).get(Material_.UNIDADE_MEDIDA).get(UnidadeMedida_.ID), unidadeMedidaId));
            }
        }

        TypedQuery<SaldoEstoqueAlmoxarifado> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<SaldoEstoqueAlmoxarifado> resultList = typedQuery.getResultList();
        long count = estoqueAlmoxarifadoRepository.count();

        return new PageImpl<>(resultList, pageable, count);
    }
}
