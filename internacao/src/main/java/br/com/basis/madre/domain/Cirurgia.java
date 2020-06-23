package br.com.basis.madre.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Cirurgia.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "cirurgia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cirurgia")
public class Cirurgia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "tempo_max")
    private Instant tempoMax;

    @Column(name = "tempo_min")
    private Instant tempoMin;

    @Column(name = "limite_dias")
    private Integer limiteDias;

    @Column(name = "limte_dias_convenios")
    private Integer limteDiasConvenios;

    @Column(name = "intervalocirurgia")
    private Integer intervalocirurgia;

    @Column(name = "intervalo_procedimento")
    private Integer intervaloProcedimento;

    public Cirurgia tempoMax(Instant tempoMax) {
        this.tempoMax = tempoMax;
        return this;
    }

    public Cirurgia tempoMin(Instant tempoMin) {
        this.tempoMin = tempoMin;
        return this;
    }

    public Cirurgia limiteDias(Integer limiteDias) {
        this.limiteDias = limiteDias;
        return this;
    }

    public Cirurgia limteDiasConvenios(Integer limteDiasConvenios) {
        this.limteDiasConvenios = limteDiasConvenios;
        return this;
    }

    public Cirurgia intervalocirurgia(Integer intervalocirurgia) {
        this.intervalocirurgia = intervalocirurgia;
        return this;
    }

    public Cirurgia intervaloProcedimento(Integer intervaloProcedimento) {
        this.intervaloProcedimento = intervaloProcedimento;
        return this;
    }

}
