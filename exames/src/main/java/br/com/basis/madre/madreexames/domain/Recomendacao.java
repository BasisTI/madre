package br.com.basis.madre.madreexames.domain;

import br.com.basis.madre.madreexames.domain.enumeration.Abrangencia;
import br.com.basis.madre.madreexames.domain.enumeration.Responsavel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Recomendacao.
 */
@Entity
@Table(name = "recomendacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "recomendacao")
public class Recomendacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRecomendacao")
    @SequenceGenerator(name = "seqRecomendacao")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "aviso_responsavel", nullable = false)
    private Boolean avisoResponsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsavel")
    private Responsavel responsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "abrangencia")
    private Abrangencia abrangencia;

    @ManyToOne
    @JsonIgnoreProperties(value = "materials", allowSetters = true)
    private Material material;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Recomendacao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAvisoResponsavel() {
        return avisoResponsavel;
    }

    public Recomendacao avisoResponsavel(Boolean avisoResponsavel) {
        this.avisoResponsavel = avisoResponsavel;
        return this;
    }

    public void setAvisoResponsavel(Boolean avisoResponsavel) {
        this.avisoResponsavel = avisoResponsavel;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public Recomendacao responsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public Abrangencia getAbrangencia() {
        return abrangencia;
    }

    public Recomendacao abrangencia(Abrangencia abrangencia) {
        this.abrangencia = abrangencia;
        return this;
    }

    public void setAbrangencia(Abrangencia abrangencia) {
        this.abrangencia = abrangencia;
    }

    public Material getMaterial() {
        return material;
    }

    public Recomendacao material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recomendacao)) {
            return false;
        }
        return id != null && id.equals(((Recomendacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recomendacao{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", avisoResponsavel='" + isAvisoResponsavel() + "'" +
            ", responsavel='" + getResponsavel() + "'" +
            ", abrangencia='" + getAbrangencia() + "'" +
            "}";
    }
}
