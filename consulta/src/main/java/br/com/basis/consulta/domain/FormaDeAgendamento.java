package br.com.basis.consulta.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A FormaDeAgendamento.
 */
@Entity
@Table(name = "forma_de_agendamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "formadeagendamento")
public class FormaDeAgendamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "numero_autorizacao", nullable = false)
    private Long numeroAutorizacao;

    @NotNull
    @Column(name = "autorizacao", nullable = false)
    private String autorizacao;

    @OneToOne(mappedBy = "formaDeAgendamento")
    @JsonIgnore
    private Emergencia emergencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroAutorizacao() {
        return numeroAutorizacao;
    }

    public FormaDeAgendamento numeroAutorizacao(Long numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
        return this;
    }

    public void setNumeroAutorizacao(Long numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
    }

    public String getAutorizacao() {
        return autorizacao;
    }

    public FormaDeAgendamento autorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
        return this;
    }

    public void setAutorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
    }

    public Emergencia getEmergencia() {
        return emergencia;
    }

    public FormaDeAgendamento emergencia(Emergencia emergencia) {
        this.emergencia = emergencia;
        return this;
    }

    public void setEmergencia(Emergencia emergencia) {
        this.emergencia = emergencia;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormaDeAgendamento)) {
            return false;
        }
        return id != null && id.equals(((FormaDeAgendamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FormaDeAgendamento{" +
            "id=" + getId() +
            ", numeroAutorizacao=" + getNumeroAutorizacao() +
            ", autorizacao='" + getAutorizacao() + "'" +
            "}";
    }
}
