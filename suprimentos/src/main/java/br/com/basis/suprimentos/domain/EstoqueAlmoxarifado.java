package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstoqueAlmoxarifado.
 */
@Entity
@Table(name = "estoque_almoxarifado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "estoquealmoxarifado")
public class EstoqueAlmoxarifado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Size(min = 1, max = 8)
    @Column(name = "endereco", length = 8)
    private String endereco;

    @Column(name = "quantidade_limite_armazenamento")
    private Long quantidadeLimiteArmazenamento;

    @NotNull
    @Min(value = 1L)
    @Column(name = "quantidade_estoque_minimo", nullable = false)
    private Long quantidadeEstoqueMinimo;

    @NotNull
    @Min(value = 1L)
    @Column(name = "quantidade_estoque_maximo", nullable = false)
    private Long quantidadeEstoqueMaximo;

    @NotNull
    @Min(value = 1L)
    @Column(name = "quantidade_ponto_pedido", nullable = false)
    private Long quantidadePontoPedido;

    @NotNull
    @Min(value = 1)
    @Column(name = "tempo_reposicao", nullable = false)
    private Integer tempoReposicao;

    @OneToMany(mappedBy = "estoque")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lote> lotes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("estoques")
    private Almoxarifado almoxarifado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("estoqueAlmoxarifados")
    private Material material;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("estoqueAlmoxarifados")
    private Fornecedor fornecedor;

    @ManyToOne
    @JsonIgnoreProperties("estoqueAlmoxarifados")
    private SolicitacaoCompras solicitacaoCompras;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public EstoqueAlmoxarifado ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getEndereco() {
        return endereco;
    }

    public EstoqueAlmoxarifado endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getQuantidadeLimiteArmazenamento() {
        return quantidadeLimiteArmazenamento;
    }

    public EstoqueAlmoxarifado quantidadeLimiteArmazenamento(Long quantidadeLimiteArmazenamento) {
        this.quantidadeLimiteArmazenamento = quantidadeLimiteArmazenamento;
        return this;
    }

    public void setQuantidadeLimiteArmazenamento(Long quantidadeLimiteArmazenamento) {
        this.quantidadeLimiteArmazenamento = quantidadeLimiteArmazenamento;
    }

    public Long getQuantidadeEstoqueMinimo() {
        return quantidadeEstoqueMinimo;
    }

    public EstoqueAlmoxarifado quantidadeEstoqueMinimo(Long quantidadeEstoqueMinimo) {
        this.quantidadeEstoqueMinimo = quantidadeEstoqueMinimo;
        return this;
    }

    public void setQuantidadeEstoqueMinimo(Long quantidadeEstoqueMinimo) {
        this.quantidadeEstoqueMinimo = quantidadeEstoqueMinimo;
    }

    public Long getQuantidadeEstoqueMaximo() {
        return quantidadeEstoqueMaximo;
    }

    public EstoqueAlmoxarifado quantidadeEstoqueMaximo(Long quantidadeEstoqueMaximo) {
        this.quantidadeEstoqueMaximo = quantidadeEstoqueMaximo;
        return this;
    }

    public void setQuantidadeEstoqueMaximo(Long quantidadeEstoqueMaximo) {
        this.quantidadeEstoqueMaximo = quantidadeEstoqueMaximo;
    }

    public Long getQuantidadePontoPedido() {
        return quantidadePontoPedido;
    }

    public EstoqueAlmoxarifado quantidadePontoPedido(Long quantidadePontoPedido) {
        this.quantidadePontoPedido = quantidadePontoPedido;
        return this;
    }

    public void setQuantidadePontoPedido(Long quantidadePontoPedido) {
        this.quantidadePontoPedido = quantidadePontoPedido;
    }

    public Integer getTempoReposicao() {
        return tempoReposicao;
    }

    public EstoqueAlmoxarifado tempoReposicao(Integer tempoReposicao) {
        this.tempoReposicao = tempoReposicao;
        return this;
    }

    public void setTempoReposicao(Integer tempoReposicao) {
        this.tempoReposicao = tempoReposicao;
    }

    public Set<Lote> getLotes() {
        return lotes;
    }

    public EstoqueAlmoxarifado lotes(Set<Lote> lotes) {
        this.lotes = lotes;
        return this;
    }

    public EstoqueAlmoxarifado addLotes(Lote lote) {
        this.lotes.add(lote);
        lote.setEstoque(this);
        return this;
    }

    public EstoqueAlmoxarifado removeLotes(Lote lote) {
        this.lotes.remove(lote);
        lote.setEstoque(null);
        return this;
    }

    public void setLotes(Set<Lote> lotes) {
        this.lotes = lotes;
    }

    public Almoxarifado getAlmoxarifado() {
        return almoxarifado;
    }

    public EstoqueAlmoxarifado almoxarifado(Almoxarifado almoxarifado) {
        this.almoxarifado = almoxarifado;
        return this;
    }

    public void setAlmoxarifado(Almoxarifado almoxarifado) {
        this.almoxarifado = almoxarifado;
    }

    public Material getMaterial() {
        return material;
    }

    public EstoqueAlmoxarifado material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public EstoqueAlmoxarifado fornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        return this;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public SolicitacaoCompras getSolicitacaoCompras() {
        return solicitacaoCompras;
    }

    public EstoqueAlmoxarifado solicitacaoCompras(SolicitacaoCompras solicitacaoCompras) {
        this.solicitacaoCompras = solicitacaoCompras;
        return this;
    }

    public void setSolicitacaoCompras(SolicitacaoCompras solicitacaoCompras) {
        this.solicitacaoCompras = solicitacaoCompras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstoqueAlmoxarifado)) {
            return false;
        }
        return id != null && id.equals(((EstoqueAlmoxarifado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstoqueAlmoxarifado{" +
            "id=" + getId() +
            ", ativo='" + isAtivo() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", quantidadeLimiteArmazenamento=" + getQuantidadeLimiteArmazenamento() +
            ", quantidadeEstoqueMinimo=" + getQuantidadeEstoqueMinimo() +
            ", quantidadeEstoqueMaximo=" + getQuantidadeEstoqueMaximo() +
            ", quantidadePontoPedido=" + getQuantidadePontoPedido() +
            ", tempoReposicao=" + getTempoReposicao() +
            "}";
    }
}
