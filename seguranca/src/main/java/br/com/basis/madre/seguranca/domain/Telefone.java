package br.com.basis.madre.seguranca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import br.com.basis.madre.seguranca.domain.enumeration.TipoDoContato;

/**
 * A Telefone.
 */
@Entity
@Table(name = "telefone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-telefone")
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTelefone")
    @SequenceGenerator(name = "seqTelefone")
    private Long id;

    @Column(name = "ddd")
    private String ddd;

    @Column(name = "numero")
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoDoContato tipo;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "ramal")
    private String ramal;

    @ManyToOne
    @JsonIgnoreProperties(value = "telefones", allowSetters = true)
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDdd() {
        return ddd;
    }

    public Telefone ddd(String ddd) {
        this.ddd = ddd;
        return this;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public Telefone numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoDoContato getTipo() {
        return tipo;
    }

    public Telefone tipo(TipoDoContato tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoDoContato tipo) {
        this.tipo = tipo;
    }

    public String getObservacao() {
        return observacao;
    }

    public Telefone observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getRamal() {
        return ramal;
    }

    public Telefone ramal(String ramal) {
        this.ramal = ramal;
        return this;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Telefone pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Telefone)) {
            return false;
        }
        return id != null && id.equals(((Telefone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Telefone{" +
            "id=" + getId() +
            ", ddd='" + getDdd() + "'" +
            ", numero='" + getNumero() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", ramal='" + getRamal() + "'" +
            "}";
    }
}
