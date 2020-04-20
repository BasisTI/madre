package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;


import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import br.com.basis.madre.domain.enumeration.DocumentoDeReferencia;

/**
 * A CartaoSUS.
 */

@Entity
@Table(name = "cartao_sus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cartaosus")
public class CartaoSUS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @NotNull
    @br.com.basis.madre.domain.validation.annotation.CartaoSUS
    @Column(name = "numero", nullable = false)
    private String numero;

    @Field(type = FieldType.Keyword)
    @Enumerated(EnumType.STRING)
    @Column(name = "documento_de_referencia")
    private DocumentoDeReferencia documentoDeReferencia;

    @Field(type = FieldType.Text)
    @Column(name = "cartao_nacional_saude_mae")
    private String cartaoNacionalSaudeMae;

    @Field(type = FieldType.Date)
    @Column(name = "data_de_entrada_no_brasil")
    private LocalDate dataDeEntradaNoBrasil;

    @Field(type = FieldType.Date)
    @Column(name = "data_de_naturalizacao")
    private LocalDate dataDeNaturalizacao;

    @Field(type = FieldType.Text)
    @Column(name = "portaria")
    private String portaria;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("cartaoSUSES")
    private Justificativa justificativa;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("cartaoSUSES")
    private MotivoDoCadastro motivoDoCadastro;

//    @Field(type = FieldType.Nested)
    @OneToOne(mappedBy = "cartaoSUS")
    @JsonIgnore
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public CartaoSUS numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public DocumentoDeReferencia getDocumentoDeReferencia() {
        return documentoDeReferencia;
    }

    public CartaoSUS documentoDeReferencia(DocumentoDeReferencia documentoDeReferencia) {
        this.documentoDeReferencia = documentoDeReferencia;
        return this;
    }

    public void setDocumentoDeReferencia(DocumentoDeReferencia documentoDeReferencia) {
        this.documentoDeReferencia = documentoDeReferencia;
    }

    public String getCartaoNacionalSaudeMae() {
        return cartaoNacionalSaudeMae;
    }

    public CartaoSUS cartaoNacionalSaudeMae(String cartaoNacionalSaudeMae) {
        this.cartaoNacionalSaudeMae = cartaoNacionalSaudeMae;
        return this;
    }

    public void setCartaoNacionalSaudeMae(String cartaoNacionalSaudeMae) {
        this.cartaoNacionalSaudeMae = cartaoNacionalSaudeMae;
    }

    public LocalDate getDataDeEntradaNoBrasil() {
        return dataDeEntradaNoBrasil;
    }

    public CartaoSUS dataDeEntradaNoBrasil(LocalDate dataDeEntradaNoBrasil) {
        this.dataDeEntradaNoBrasil = dataDeEntradaNoBrasil;
        return this;
    }

    public void setDataDeEntradaNoBrasil(LocalDate dataDeEntradaNoBrasil) {
        this.dataDeEntradaNoBrasil = dataDeEntradaNoBrasil;
    }

    public LocalDate getDataDeNaturalizacao() {
        return dataDeNaturalizacao;
    }

    public CartaoSUS dataDeNaturalizacao(LocalDate dataDeNaturalizacao) {
        this.dataDeNaturalizacao = dataDeNaturalizacao;
        return this;
    }

    public void setDataDeNaturalizacao(LocalDate dataDeNaturalizacao) {
        this.dataDeNaturalizacao = dataDeNaturalizacao;
    }

    public String getPortaria() {
        return portaria;
    }

    public CartaoSUS portaria(String portaria) {
        this.portaria = portaria;
        return this;
    }

    public void setPortaria(String portaria) {
        this.portaria = portaria;
    }

    public Justificativa getJustificativa() {
        return justificativa;
    }

    public CartaoSUS justificativa(Justificativa justificativa) {
        this.justificativa = justificativa;
        return this;
    }

    public void setJustificativa(Justificativa justificativa) {
        this.justificativa = justificativa;
    }

    public MotivoDoCadastro getMotivoDoCadastro() {
        return motivoDoCadastro;
    }

    public CartaoSUS motivoDoCadastro(MotivoDoCadastro motivoDoCadastro) {
        this.motivoDoCadastro = motivoDoCadastro;
        return this;
    }

    public void setMotivoDoCadastro(MotivoDoCadastro motivoDoCadastro) {
        this.motivoDoCadastro = motivoDoCadastro;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public CartaoSUS paciente(Paciente paciente) {
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
        if (!(o instanceof CartaoSUS)) {
            return false;
        }
        return id != null && id.equals(((CartaoSUS) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CartaoSUS{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", documentoDeReferencia='" + getDocumentoDeReferencia() + "'" +
            ", cartaoNacionalSaudeMae='" + getCartaoNacionalSaudeMae() + "'" +
            ", dataDeEntradaNoBrasil='" + getDataDeEntradaNoBrasil() + "'" +
            ", dataDeNaturalizacao='" + getDataDeNaturalizacao() + "'" +
            ", portaria='" + getPortaria() + "'" +
            "}";
    }
}
