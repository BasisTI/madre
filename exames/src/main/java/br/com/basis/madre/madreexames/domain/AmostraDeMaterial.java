package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import br.com.basis.madre.madreexames.domain.enumeration.OrigemTipoAmostra;

import br.com.basis.madre.madreexames.domain.enumeration.UnidadeDeMedida;

import br.com.basis.madre.madreexames.domain.enumeration.Responsavel;

/**
 * A AmostraDeMaterial.
 */
@Entity
@Table(name = "amostra_de_material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "amostradematerial")
public class AmostraDeMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAmostraDeMaterial")
    @SequenceGenerator(name = "seqAmostraDeMaterial")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "origem", nullable = false)
    private OrigemTipoAmostra origem;

    @NotNull
    @Column(name = "numero_de_amostras", nullable = false)
    private Integer numeroDeAmostras;

    @Column(name = "volume_da_amostra")
    private Integer volumeDaAmostra;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_de_medida", nullable = false)
    private UnidadeDeMedida unidadeDeMedida;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "responsavel", nullable = false)
    private Responsavel responsavel;

    @NotNull
    @Column(name = "congelado", nullable = false)
    private Boolean congelado;

    @Column(name = "unidade_funcional_id")
    private Integer unidadeFuncionalId;

    @ManyToOne
    @JsonIgnoreProperties(value = "amostraDeMaterials", allowSetters = true)
    private Recipiente amostraRecipiente;

    @ManyToOne
    @JsonIgnoreProperties(value = "amostraDeMaterials", allowSetters = true)
    private Anticoagulante amostraAnticoagulante;

    @ManyToOne
    @JsonIgnoreProperties(value = "amostraDeMaterials", allowSetters = true)
    private Material amostraMaterial;

    @ManyToOne
    @JsonIgnoreProperties(value = "amostras", allowSetters = true)
    private MaterialDeExame materialDeExame;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public AmostraDeMaterial nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public OrigemTipoAmostra getOrigem() {
        return origem;
    }

    public AmostraDeMaterial origem(OrigemTipoAmostra origem) {
        this.origem = origem;
        return this;
    }

    public void setOrigem(OrigemTipoAmostra origem) {
        this.origem = origem;
    }

    public Integer getNumeroDeAmostras() {
        return numeroDeAmostras;
    }

    public AmostraDeMaterial numeroDeAmostras(Integer numeroDeAmostras) {
        this.numeroDeAmostras = numeroDeAmostras;
        return this;
    }

    public void setNumeroDeAmostras(Integer numeroDeAmostras) {
        this.numeroDeAmostras = numeroDeAmostras;
    }

    public Integer getVolumeDaAmostra() {
        return volumeDaAmostra;
    }

    public AmostraDeMaterial volumeDaAmostra(Integer volumeDaAmostra) {
        this.volumeDaAmostra = volumeDaAmostra;
        return this;
    }

    public void setVolumeDaAmostra(Integer volumeDaAmostra) {
        this.volumeDaAmostra = volumeDaAmostra;
    }

    public UnidadeDeMedida getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public AmostraDeMaterial unidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
        return this;
    }

    public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public AmostraDeMaterial responsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public Boolean isCongelado() {
        return congelado;
    }

    public AmostraDeMaterial congelado(Boolean congelado) {
        this.congelado = congelado;
        return this;
    }

    public void setCongelado(Boolean congelado) {
        this.congelado = congelado;
    }

    public Integer getUnidadeFuncionalId() {
        return unidadeFuncionalId;
    }

    public AmostraDeMaterial unidadeFuncionalId(Integer unidadeFuncionalId) {
        this.unidadeFuncionalId = unidadeFuncionalId;
        return this;
    }

    public void setUnidadeFuncionalId(Integer unidadeFuncionalId) {
        this.unidadeFuncionalId = unidadeFuncionalId;
    }

    public Recipiente getAmostraRecipiente() {
        return amostraRecipiente;
    }

    public AmostraDeMaterial amostraRecipiente(Recipiente recipiente) {
        this.amostraRecipiente = recipiente;
        return this;
    }

    public void setAmostraRecipiente(Recipiente recipiente) {
        this.amostraRecipiente = recipiente;
    }

    public Anticoagulante getAmostraAnticoagulante() {
        return amostraAnticoagulante;
    }

    public AmostraDeMaterial amostraAnticoagulante(Anticoagulante anticoagulante) {
        this.amostraAnticoagulante = anticoagulante;
        return this;
    }

    public void setAmostraAnticoagulante(Anticoagulante anticoagulante) {
        this.amostraAnticoagulante = anticoagulante;
    }

    public Material getAmostraMaterial() {
        return amostraMaterial;
    }

    public AmostraDeMaterial amostraMaterial(Material material) {
        this.amostraMaterial = material;
        return this;
    }

    public void setAmostraMaterial(Material material) {
        this.amostraMaterial = material;
    }

    public MaterialDeExame getMaterialDeExame() {
        return materialDeExame;
    }

    public AmostraDeMaterial materialDeExame(MaterialDeExame materialDeExame) {
        this.materialDeExame = materialDeExame;
        return this;
    }

    public void setMaterialDeExame(MaterialDeExame materialDeExame) {
        this.materialDeExame = materialDeExame;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmostraDeMaterial)) {
            return false;
        }
        return id != null && id.equals(((AmostraDeMaterial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmostraDeMaterial{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", numeroDeAmostras=" + getNumeroDeAmostras() +
            ", volumeDaAmostra=" + getVolumeDaAmostra() +
            ", unidadeDeMedida='" + getUnidadeDeMedida() + "'" +
            ", responsavel='" + getResponsavel() + "'" +
            ", congelado='" + isCongelado() + "'" +
            ", unidadeFuncionalId=" + getUnidadeFuncionalId() +
            "}";
    }
}
