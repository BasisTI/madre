package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.DocumentoDeReferencia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CartaoSUS.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "cartao_sus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-cartaosus")
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

    @OneToOne(mappedBy = "cartaoSUS")
    @JsonIgnore
    private Paciente paciente;

    public CartaoSUS numero(String numero) {
        this.numero = numero;
        return this;
    }

    public CartaoSUS documentoDeReferencia(DocumentoDeReferencia documentoDeReferencia) {
        this.documentoDeReferencia = documentoDeReferencia;
        return this;
    }

    public CartaoSUS cartaoNacionalSaudeMae(String cartaoNacionalSaudeMae) {
        this.cartaoNacionalSaudeMae = cartaoNacionalSaudeMae;
        return this;
    }

    public CartaoSUS dataDeEntradaNoBrasil(LocalDate dataDeEntradaNoBrasil) {
        this.dataDeEntradaNoBrasil = dataDeEntradaNoBrasil;
        return this;
    }

    public CartaoSUS dataDeNaturalizacao(LocalDate dataDeNaturalizacao) {
        this.dataDeNaturalizacao = dataDeNaturalizacao;
        return this;
    }

    public CartaoSUS portaria(String portaria) {
        this.portaria = portaria;
        return this;
    }

    public CartaoSUS justificativa(Justificativa justificativa) {
        this.justificativa = justificativa;
        return this;
    }

    public CartaoSUS motivoDoCadastro(MotivoDoCadastro motivoDoCadastro) {
        this.motivoDoCadastro = motivoDoCadastro;
        return this;
    }

    public CartaoSUS paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

}
