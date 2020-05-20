package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Entity
@Table(name = "leito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "leito")
public class Leito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "ala", nullable = false)
    private Integer ala;

    @NotNull
    @Column(name = "andar", nullable = false)
    private Integer andar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("leitos")
    private UnidadeFuncional unidadeFuncional;

    public Leito nome(String nome) {
        this.nome = nome;
        return this;
    }

    public Leito ala(Integer ala) {
        this.ala = ala;
        return this;
    }

    public Leito andar(Integer andar) {
        this.andar = andar;
        return this;
    }

    public Leito unidadeFuncional(UnidadeFuncional unidadeFuncional) {
        this.unidadeFuncional = unidadeFuncional;
        return this;
    }

}
