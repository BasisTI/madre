package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A TipoDeMarcacao.
 */
@Entity
@Table(name = "tipo_de_marcacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tipodemarcacao")
public class TipoDeMarcacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTipoDeMarcacao")
    @SequenceGenerator(name = "seqTipoDeMarcacao")
    private Long id;

    @Column(name = "tipo_de_marcacao_nome")
    private String tipoDeMarcacaoNome;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDeMarcacaoNome() {
        return tipoDeMarcacaoNome;
    }

    public TipoDeMarcacao tipoDeMarcacaoNome(String tipoDeMarcacaoNome) {
        this.tipoDeMarcacaoNome = tipoDeMarcacaoNome;
        return this;
    }

    public void setTipoDeMarcacaoNome(String tipoDeMarcacaoNome) {
        this.tipoDeMarcacaoNome = tipoDeMarcacaoNome;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public TipoDeMarcacao ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDeMarcacao)) {
            return false;
        }
        return id != null && id.equals(((TipoDeMarcacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDeMarcacao{" +
            "id=" + getId() +
            ", tipoDeMarcacaoNome='" + getTipoDeMarcacaoNome() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
