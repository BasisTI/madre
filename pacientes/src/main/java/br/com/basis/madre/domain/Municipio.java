package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Municipio.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "municipio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-municipio")
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Field(type = FieldType.Text)
    @Column(name = "nome_do_distrito")
    private String nomeDoDistrito;


    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "ibge", nullable = false)
    private String ibge;

    @Field(type = FieldType.Object)
    @ManyToOne
    @JsonIgnoreProperties("municipios")
    private UF uf;

    public Municipio nome(String nome) {
        this.nome = nome;
        return this;
    }

    public Municipio nomeDoDistrito(String nomeDoDistrito) {
        this.nomeDoDistrito = nomeDoDistrito;
        return this;
    }

    public Municipio ibge(String ibge) {
        this.ibge = ibge;
        return this;
    }

    public Municipio uf(UF uF) {
        this.uf = uF;
        return this;
    }


}
