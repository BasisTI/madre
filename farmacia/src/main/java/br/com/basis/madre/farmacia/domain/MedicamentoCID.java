package br.com.basis.madre.farmacia.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MedicamentoCID.
 */
@Entity
@Table(name = "medicamento_cid")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "medicamentocid")
public class MedicamentoCID implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")

    private Long id;

    @Column(name = "codigo_medicamento")
    private String codigoMedicamento;

    @Column(name = "codigo_cid")
    private String codigoCID;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public MedicamentoCID codigoMedicamento(String codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
        return this;
    }

    public void setCodigoMedicamento(String codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }

    public String getCodigoCID() {
        return codigoCID;
    }

    public MedicamentoCID codigoCID(String codigoCID) {
        this.codigoCID = codigoCID;
        return this;
    }

    public void setCodigoCID(String codigoCID) {
        this.codigoCID = codigoCID;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicamentoCID)) {
            return false;
        }
        return id != null && id.equals(((MedicamentoCID) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicamentoCID{" +
            "id=" + getId() +
            ", codigoMedicamento='" + getCodigoMedicamento() + "'" +
            ", codigoCID='" + getCodigoCID() + "'" +
            "}";
    }
}
