package br.com.basis.madre.prescricao.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Diluente.
 */
@Entity
@Table(name = "diluente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "diluente")
public class Diluente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    @ManyToOne
    @JsonIgnoreProperties("diluentes")
    private PrescricaoMedicamento prescricaoMedicamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Diluente descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PrescricaoMedicamento getPrescricaoMedicamento() {
        return prescricaoMedicamento;
    }

    public Diluente prescricaoMedicamento(PrescricaoMedicamento prescricaoMedicamento) {
        this.prescricaoMedicamento = prescricaoMedicamento;
        return this;
    }

    public void setPrescricaoMedicamento(PrescricaoMedicamento prescricaoMedicamento) {
        this.prescricaoMedicamento = prescricaoMedicamento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diluente)) {
            return false;
        }
        return id != null && id.equals(((Diluente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Diluente{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
