package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

import br.com.basis.madre.domain.enumeration.TipoDoEndereco;

/**
 * A Endereco.
 */

@Entity
@Table(name = "endereco")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    @Field(type = FieldType.Text)

    @Size(min = 8, max = 8)

    @NotNull
    @Column(name = "cep", nullable = false)
    private String cep;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @Field(type = FieldType.Text)
    @Column(name = "complemento")
    private String complemento;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Field(type = FieldType.Boolean)
    @NotNull
    @Column(name = "correspondencia", nullable = false)
    private Boolean correspondencia;

    @Field(type = FieldType.Keyword)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_do_endereco")
    private TipoDoEndereco tipoDoEndereco;

//    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("enderecos")
    private Municipio municipio;

//    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("enderecos")
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public Endereco cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Endereco logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public Endereco numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public Endereco complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public Endereco bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Boolean isCorrespondencia() {
        return correspondencia;
    }

    public Endereco correspondencia(Boolean correspondencia) {
        this.correspondencia = correspondencia;
        return this;
    }

    public void setCorrespondencia(Boolean correspondencia) {
        this.correspondencia = correspondencia;
    }

    public TipoDoEndereco getTipoDoEndereco() {
        return tipoDoEndereco;
    }

    public Endereco tipoDoEndereco(TipoDoEndereco tipoDoEndereco) {
        this.tipoDoEndereco = tipoDoEndereco;
        return this;
    }

    public void setTipoDoEndereco(TipoDoEndereco tipoDoEndereco) {
        this.tipoDoEndereco = tipoDoEndereco;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public Endereco municipio(Municipio municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Endereco paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Endereco)) {
            return false;
        }
        return id != null && id.equals(((Endereco) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Endereco{" +
            "id=" + getId() +
            ", cep='" + getCep() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", correspondencia='" + isCorrespondencia() + "'" +
            ", tipoDoEndereco='" + getTipoDoEndereco() + "'" +
            "}";
    }
}
