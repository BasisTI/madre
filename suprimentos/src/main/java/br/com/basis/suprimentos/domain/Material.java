package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
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

    public Material nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Material descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Material regimento(String regimento) {
        this.regimento = regimento;
        return this;
    }

    public void setRegimento(String regimento) {
        this.regimento = regimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public Material observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getUnidadeId() {
        return unidadeId;
    }

    public Material unidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
        return this;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    public Long getProcedimentoId() {
        return procedimentoId;
    }

    public Material procedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
        return this;
    }

    public void setProcedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public Material unidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
        return this;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public GrupoMaterial getGrupo() {
        return grupo;
    }

    public Material grupo(GrupoMaterial grupoMaterial) {
        this.grupo = grupoMaterial;
        return this;
    }

    public void setGrupo(GrupoMaterial grupoMaterial) {
        this.grupo = grupoMaterial;
    }

    public Almoxarifado getLocalEstoque() {
        return localEstoque;
    }

    public Material localEstoque(Almoxarifado almoxarifado) {
        this.localEstoque = almoxarifado;
        return this;
    }

    public void setLocalEstoque(Almoxarifado almoxarifado) {
        this.localEstoque = almoxarifado;
    }

    public CodigoCatmat getCodigoCatmat() {
        return codigoCatmat;
    }

    public Material codigoCatmat(CodigoCatmat codigoCatmat) {
        this.codigoCatmat = codigoCatmat;
        return this;
    }

    public void setCodigoCatmat(CodigoCatmat codigoCatmat) {
        this.codigoCatmat = codigoCatmat;
    }

    public Sazonalidade getSazonalidade() {
        return sazonalidade;
    }

    public Material sazonalidade(Sazonalidade sazonalidade) {
        this.sazonalidade = sazonalidade;
        return this;
    }

    public void setSazonalidade(Sazonalidade sazonalidade) {
        this.sazonalidade = sazonalidade;
    }

    public TipoResiduo getTipoResiduo() {
        return tipoResiduo;
    }

    public Material tipoResiduo(TipoResiduo tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
        return this;
    }

    public void setTipoResiduo(TipoResiduo tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public OrigemParecerTecnico getOrigemParecerTecnico() {
        return origemParecerTecnico;
    }

    public Material origemParecerTecnico(OrigemParecerTecnico origemParecerTecnico) {
        this.origemParecerTecnico = origemParecerTecnico;
        return this;
    }

    public void setOrigemParecerTecnico(OrigemParecerTecnico origemParecerTecnico) {
        this.origemParecerTecnico = origemParecerTecnico;
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
