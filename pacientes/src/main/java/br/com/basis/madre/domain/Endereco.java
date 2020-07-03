package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.TipoDoEndereco;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Endereco.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "endereco")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    @Field(type = FieldType.Text)


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

    @ManyToOne
    @JsonIgnoreProperties("enderecos")
    private Municipio municipio;

    @ManyToOne
    @JsonIgnoreProperties("enderecos")
    private Paciente paciente;

    public Endereco cep(String cep) {
        this.cep = cep;
        return this;
    }

    public Endereco logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public Endereco numero(String numero) {
        this.numero = numero;
        return this;
    }

    public Endereco complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public Endereco bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public Endereco correspondencia(Boolean correspondencia) {
        this.correspondencia = correspondencia;
        return this;
    }

    public Endereco tipoDoEndereco(TipoDoEndereco tipoDoEndereco) {
        this.tipoDoEndereco = tipoDoEndereco;
        return this;
    }

    public Endereco municipio(Municipio municipio) {
        this.municipio = municipio;
        return this;
    }

    public Endereco paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public Boolean isCorrespondencia() {
        return correspondencia;
    }

}
