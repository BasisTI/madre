package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Almoxarifado_;
import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Optional;

import static br.com.basis.suprimentos.domain.EstoqueAlmoxarifado_.ALMOXARIFADO;
import static br.com.basis.suprimentos.domain.EstoqueAlmoxarifado_.ATIVO;
import static br.com.basis.suprimentos.domain.EstoqueAlmoxarifado_.ESTOCAVEL;
import static br.com.basis.suprimentos.domain.EstoqueAlmoxarifado_.FORNECEDOR;
import static br.com.basis.suprimentos.domain.EstoqueAlmoxarifado_.ID;
import static br.com.basis.suprimentos.domain.EstoqueAlmoxarifado_.LOTES;
import static br.com.basis.suprimentos.domain.EstoqueAlmoxarifado_.MATERIAL;
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
    private final PageableQueryService<SaldoEstoqueAlmoxarifado> pageableQueryService;

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

    public CompoundSelection<SaldoEstoqueAlmoxarifado> getSaldoEstoqueAlmoxarifadoSelection(CriteriaBuilder cb, Root<EstoqueAlmoxarifado> root) {
        return cb.construct(
                SaldoEstoqueAlmoxarifado.class,
                root.get(ID),
                root.get(ATIVO),
                root.get(ESTOCAVEL),
                cb.sum(root.join(LOTES).get(Lote_.QUANTIDADE_DISPONIVEL)),
                cb.sum(root.join(LOTES).get(Lote_.QUANTIDADE_BLOQUEADA)),
                cb.sum(root.join(LOTES).get(Lote_.QUANTIDADE_PROBLEMA)),
                root.join(MATERIAL).get(Material_.NOME).alias("nomeMaterial"),
                root.join(FORNECEDOR).get(Fornecedor_.NOME_FANTASIA).alias("nomeFantasia"),
                root.join(ALMOXARIFADO).get(Almoxarifado_.DESCRICAO).alias("nomeAlmoxarifado")
        );
    }

    @Transactional(readOnly = true)
    public Page<SaldoEstoqueAlmoxarifado> obterSaldoTodosEstoques(ConsultaEstoqueAlmoxarifadoDTO consultaEstoqueAlmoxarifadoDTO, Pageable pageable) {
        Long fornecedorId = consultaEstoqueAlmoxarifadoDTO.getFornecedorId();
        Long almoxarifadoId = consultaEstoqueAlmoxarifadoDTO.getAlmoxarifadoId();
        Long grupoId = consultaEstoqueAlmoxarifadoDTO.getGrupoId();
        Long unidadeMedidaId = consultaEstoqueAlmoxarifadoDTO.getUnidadeMedidaId();
        Long materialId = consultaEstoqueAlmoxarifadoDTO.getMaterialId();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SaldoEstoqueAlmoxarifado> query = cb.createQuery(SaldoEstoqueAlmoxarifado.class);
        Root<EstoqueAlmoxarifado> root = query.from(EstoqueAlmoxarifado.class);

        query.select(getSaldoEstoqueAlmoxarifadoSelection(cb, root));
        query.groupBy(
                root.get(ID),
                root.get(MATERIAL).get(Material_.NOME),
                root.get(FORNECEDOR).get(Fornecedor_.NOME_FANTASIA),
                root.get(ALMOXARIFADO).get(Almoxarifado_.DESCRICAO)
        );
        query.where(cb.equal(root.get(ATIVO), consultaEstoqueAlmoxarifadoDTO.getAtivo()));
        query.where(cb.equal(root.get(ESTOCAVEL), consultaEstoqueAlmoxarifadoDTO.getEstocavel()));

        adicionarClausulaWhere(cb, query, fornecedorId, root.get(FORNECEDOR).get(Fornecedor_.ID));
        adicionarClausulaWhere(cb, query, almoxarifadoId, root.get(ALMOXARIFADO).get(Almoxarifado_.ID));

        if (materialId != null) {
            adicionarClausulaWhere(cb, query, materialId, root.get(MATERIAL).get(Material_.ID));
        } else {
            adicionarClausulaWhere(cb, query, grupoId, root.get(MATERIAL).get(Material_.GRUPO).get(GrupoMaterial_.ID));
            adicionarClausulaWhere(cb, query, unidadeMedidaId, root.get(MATERIAL).get(Material_.UNIDADE_MEDIDA).get(UnidadeMedida_.ID));
        }

        TypedQuery<SaldoEstoqueAlmoxarifado> typedQuery = pageableQueryService.usePageableToLimitResults(pageable, entityManager.createQuery(query));
        return pageableQueryService.getPageFromPageable(pageable, typedQuery.getResultList(), estoqueAlmoxarifadoRepository.count());
    }

    private <T> void adicionarClausulaWhere(CriteriaBuilder cb, CriteriaQuery<T> query, Long entidadeId, Path<Object> objectPath) {
        if (entidadeId != null) {
          query.where(cb.equal(objectPath, entidadeId));
        }
    }
}
