package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.UnidadeTempo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;


/**
 * A Prescricao.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "prescricao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-internacao-prescricao")
public class Prescricao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "horario_validade")
    private Instant horarioValidade;

    @Column(name = "tempo_adiantamento")
    private Integer tempoAdiantamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_tempo")
    private UnidadeTempo unidadeTempo;

    @Column(name = "numero_vias")
    private Integer numeroVias;

    public Prescricao horarioValidade(Instant horarioValidade) {
        this.horarioValidade = horarioValidade;
        return this;
    }

    public Prescricao tempoAdiantamento(Integer tempoAdiantamento) {
        this.tempoAdiantamento = tempoAdiantamento;
        return this;
    }

    public Prescricao unidadeTempo(UnidadeTempo unidadeTempo) {
        this.unidadeTempo = unidadeTempo;
        return this;
    }

    public Prescricao numeroVias(Integer numeroVias) {
        this.numeroVias = numeroVias;
        return this;
    }

}
