package br.com.basis.consulta.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A CondicaoDeAtendimento.
 */
@Entity
@Data
@Table(name = "condicao_de_atendimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "condicaodeatendimento")
public class CondicaoDeAtendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "sigla", nullable = false)
    private String sigla;

    @NotNull
    @Column(name = "especialidade", nullable = false)
    private String especialidade;

    @OneToOne(mappedBy = "condicaoDeAtendimento")
    @JsonIgnore
    private Emergencia emergencia;


    public CondicaoDeAtendimento sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }
    public CondicaoDeAtendimento especialidade(String especialidade) {
        this.especialidade = especialidade;
        return this;
    }
    public CondicaoDeAtendimento emergencia(Emergencia emergencia) {
        this.emergencia = emergencia;
        return this;
    }

}
