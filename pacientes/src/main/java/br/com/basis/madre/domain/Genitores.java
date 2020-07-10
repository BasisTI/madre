package br.com.basis.madre.domain;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Genitores.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "genitores")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-genitores")
public class Genitores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @Column(name = "prontuario_da_mae")
    private String prontuarioDaMae;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "nome_da_mae", nullable = false)
    private String nomeDaMae;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "nome_do_pai", nullable = false)
    private String nomeDoPai;

    public Genitores prontuarioDaMae(String prontuarioDaMae) {
        this.prontuarioDaMae = prontuarioDaMae;
        return this;
    }

    public Genitores nomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
        return this;
    }

    public Genitores nomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
        return this;
    }


}
