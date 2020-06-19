package br.com.basis.consulta.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A CondicaoDeAtendimento.
 */
@Entity
@Table(name = "condicao_de_atendimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "condicaodeatendimento")
public class CondicaoDeAtendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "sigla", nullable = false)
    private String sigla;

    @NotNull
    @Column(name = "especialidade", nullable = false)
    private String especialidade;

    @OneToOne(mappedBy = "condicaoDeAtendimento")
    @JsonIgnore
    private Emergencia emergencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public CondicaoDeAtendimento sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public CondicaoDeAtendimento especialidade(String especialidade) {
        this.especialidade = especialidade;
        return this;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Emergencia getEmergencia() {
        return emergencia;
    }

    public CondicaoDeAtendimento emergencia(Emergencia emergencia) {
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
        if (!(o instanceof CondicaoDeAtendimento)) {
            return false;
        }
        return id != null && id.equals(((CondicaoDeAtendimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CondicaoDeAtendimento{" +
            "id=" + getId() +
            ", sigla='" + getSigla() + "'" +
            ", especialidade='" + getEspecialidade() + "'" +
            "}";
    }
}
