package br.com.basis.madre.madreexames.domain;

import br.com.basis.madre.madreexames.domain.enumeration.ConvenioPlano;
import br.com.basis.madre.madreexames.service.dto.DominioCodigo;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A ControleQualidade.
 */
@Entity
@Table(name = "controle_qualidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-controlequalidade")
public class ControleQualidade extends DomainCodigo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqControleQualidade")
    @SequenceGenerator(name = "seqControleQualidade")
    private Long id;

    @NotNull
    @Column(name = "material", nullable = false)
    private String material;

    @NotNull
    @Column(name = "codigo_convenio", nullable = false)
    private String codigoConvenio;

    @NotNull
    @Column(name = "codigo_plano", nullable = false)
    private String codigoPlano;

    @Enumerated(EnumType.STRING)
    @Column(name = "convenio_plano")
    private ConvenioPlano convenioPlano;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ControleQualidade codigo(Integer codigo) {
        this.setCodigo(codigo);
        return this;
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

    public String getCodigoPlano() {
        return codigoPlano;
    }

    public ControleQualidade codigoPlano(String codigoPlano) {
        this.codigoPlano = codigoPlano;
        return this;
    }

    public void setCodigoPlano(String codigoPlano) {
        this.codigoPlano = codigoPlano;
    }

    public ConvenioPlano getConvenioPlano() {
        return convenioPlano;
    }

    public ControleQualidade convenioPlano(ConvenioPlano convenioPlano) {
        this.convenioPlano = convenioPlano;
        return this;
    }

    public void setConvenioPlano(ConvenioPlano convenioPlano) {
        this.convenioPlano = convenioPlano;
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
            ", codigoPlano='" + getCodigoPlano() + "'" +
            ", convenioPlano='" + getConvenioPlano() + "'" +
            "}";
    }
}
