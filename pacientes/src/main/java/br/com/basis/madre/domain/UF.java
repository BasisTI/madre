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
 * A UF.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "uf")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-uf")
public class UF implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "unidade_federativa", nullable = false)
    private String unidadeFederativa;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "sigla", nullable = false)
    private String sigla;

    public UF unidadeFederativa(String unidadeFederativa) {
        this.unidadeFederativa = unidadeFederativa;
        return this;
    }

    public UF sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

}
