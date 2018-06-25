package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import br.com.basis.dynamicexports.pojo.ReportObject;

/**
 * A TipoPergunta.
 */
@Entity
@Table(name = "tipo_pergunta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tipopergunta")
public class TipoPergunta implements Serializable,ReportObject {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "enunciado_pergunta", length = 255, nullable = false)
    private String enunciadoPergunta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciadoPergunta() {
        return enunciadoPergunta;
    }

    public TipoPergunta enunciadoPergunta(String enunciadoPergunta) {
        this.enunciadoPergunta = enunciadoPergunta;
        return this;
    }

    public void setEnunciadoPergunta(String enunciadoPergunta) {
        this.enunciadoPergunta = enunciadoPergunta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoPergunta tipoPergunta = (TipoPergunta) o;
        if (tipoPergunta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoPergunta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoPergunta{" +
            "id=" + getId() +
            ", enunciadoPergunta='" + getEnunciadoPergunta() + "'" +
            "}";
    }
}
