package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ControleQualidade.
 */
@Entity
@Table(name = "controle_qualidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "controlequalidade")
public class ControleQualidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqControleQualidade")
    @SequenceGenerator(name = "seqControleQualidade")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @NotNull
    @Column(name = "material", nullable = false)
    private String material;

    @NotNull
    @Column(name = "codigo_convenio", nullable = false)
    private String codigoConvenio;

    @NotNull
    @Column(name = "codigo_convenio_id", nullable = false)
    private Integer codigoConvenioId;

    @Column(name = "convenio_plano_id")
    private Integer convenioPlanoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public ControleQualidade codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMaterial() {
        return material;
    }

    public ControleQualidade material(String material) {
        this.material = material;
        return this;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCodigoConvenio() {
        return codigoConvenio;
    }

    public ControleQualidade codigoConvenio(String codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
        return this;
    }

    public void setCodigoConvenio(String codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
    }

    public Integer getCodigoConvenioId() {
        return codigoConvenioId;
    }

    public ControleQualidade codigoConvenioId(Integer codigoConvenioId) {
        this.codigoConvenioId = codigoConvenioId;
        return this;
    }

    public void setCodigoConvenioId(Integer codigoConvenioId) {
        this.codigoConvenioId = codigoConvenioId;
    }

    public Integer getConvenioPlanoId() {
        return convenioPlanoId;
    }

    public ControleQualidade convenioPlanoId(Integer convenioPlanoId) {
        this.convenioPlanoId = convenioPlanoId;
        return this;
    }

    public void setConvenioPlanoId(Integer convenioPlanoId) {
        this.convenioPlanoId = convenioPlanoId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControleQualidade)) {
            return false;
        }
        return id != null && id.equals(((ControleQualidade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControleQualidade{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", material='" + getMaterial() + "'" +
            ", codigoConvenio='" + getCodigoConvenio() + "'" +
            ", codigoConvenioId=" + getCodigoConvenioId() +
            ", convenioPlanoId=" + getConvenioPlanoId() +
            "}";
    }
}
