package br.com.basis.madre.cadastros.domain;

import br.com.basis.dynamicexports.pojo.ReportObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoResposta.
 */
@Entity
@Table(name = "tipo_resposta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tiporesposta")
public class TipoResposta implements Serializable, ReportObject {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "resposta", length = 255, nullable = false)
    private String resposta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResposta() {
        return resposta;
    }

    public TipoResposta resposta(String resposta) {
        this.resposta = resposta;
        return this;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
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
        TipoResposta tipoResposta = (TipoResposta) o;
        if (tipoResposta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoResposta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoResposta{" +
            "id=" + getId() +
            ", resposta='" + getResposta() + "'" +
            "}";
    }
}
