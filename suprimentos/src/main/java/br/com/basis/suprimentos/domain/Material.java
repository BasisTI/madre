package br.com.basis.suprimentos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Material.
 */
@Entity
@Table(name = "material")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "material")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 120)
    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Size(max = 2000)
    @Column(name = "descricao", length = 2000)
    private String descricao;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Size(max = 120)
    @Column(name = "regimento", length = 120)
    private String regimento;

    @Size(max = 500)
    @Column(name = "observacao", length = 500)
    private String observacao;

    @Min(value = 0L)
    @Column(name = "unidade_id")
    private Long unidadeId;

    @Min(value = 0L)
    @Column(name = "procedimento_id")
    private Long procedimentoId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("materials")
    private UnidadeMedida unidadeMedida;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("materials")
    private GrupoMaterial grupo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("materials")
    private Almoxarifado localEstoque;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private CodigoCatmat codigoCatmat;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private Sazonalidade sazonalidade;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private TipoResiduo tipoResiduo;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private OrigemParecerTecnico origemParecerTecnico;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Material nome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Material descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Material ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getRegimento() {
        return regimento;
    }

    public void setRegimento(String regimento) {
        this.regimento = regimento;
    }

    public Material regimento(String regimento) {
        this.regimento = regimento;
        return this;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Material observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    public Material unidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
        return this;
    }

    public Long getProcedimentoId() {
        return procedimentoId;
    }

    public void setProcedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
    }

    public Material procedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
        return this;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Material unidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
        return this;
    }

    public GrupoMaterial getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoMaterial grupoMaterial) {
        this.grupo = grupoMaterial;
    }

    public Material grupo(GrupoMaterial grupoMaterial) {
        this.grupo = grupoMaterial;
        return this;
    }

    public Almoxarifado getLocalEstoque() {
        return localEstoque;
    }

    public void setLocalEstoque(Almoxarifado almoxarifado) {
        this.localEstoque = almoxarifado;
    }

    public Material localEstoque(Almoxarifado almoxarifado) {
        this.localEstoque = almoxarifado;
        return this;
    }

    public CodigoCatmat getCodigoCatmat() {
        return codigoCatmat;
    }

    public void setCodigoCatmat(CodigoCatmat codigoCatmat) {
        this.codigoCatmat = codigoCatmat;
    }

    public Material codigoCatmat(CodigoCatmat codigoCatmat) {
        this.codigoCatmat = codigoCatmat;
        return this;
    }

    public Sazonalidade getSazonalidade() {
        return sazonalidade;
    }

    public void setSazonalidade(Sazonalidade sazonalidade) {
        this.sazonalidade = sazonalidade;
    }

    public Material sazonalidade(Sazonalidade sazonalidade) {
        this.sazonalidade = sazonalidade;
        return this;
    }

    public TipoResiduo getTipoResiduo() {
        return tipoResiduo;
    }

    public void setTipoResiduo(TipoResiduo tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public Material tipoResiduo(TipoResiduo tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
        return this;
    }

    public OrigemParecerTecnico getOrigemParecerTecnico() {
        return origemParecerTecnico;
    }

    public void setOrigemParecerTecnico(OrigemParecerTecnico origemParecerTecnico) {
        this.origemParecerTecnico = origemParecerTecnico;
    }

    public Material origemParecerTecnico(OrigemParecerTecnico origemParecerTecnico) {
        this.origemParecerTecnico = origemParecerTecnico;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Material)) {
            return false;
        }
        return id != null && id.equals(((Material) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Material{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", regimento='" + getRegimento() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", unidadeId=" + getUnidadeId() +
            ", procedimentoId=" + getProcedimentoId() +
            "}";
    }
}
