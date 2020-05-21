package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
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
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "evento_leito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eventoleito")
public class EventoLeito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "data_do_lancamento", nullable = false)
    private ZonedDateTime dataDoLancamento;

    @Column(name = "justificativa")
    private String justificativa;

    @Column(name = "data_do_inicio")
    private ZonedDateTime dataInicio;

    @Column(name = "data_do_fim")
    private ZonedDateTime dataFim;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("eventoLeitos")
    private TipoDoEventoLeito tipoDoEvento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("eventoLeitos")
    private Leito leito;

    @ManyToOne
    @JsonIgnoreProperties("eventoLeitos")
    private OrigemDaReservaDeLeito origem;

    @ManyToOne
    @JsonIgnoreProperties("eventoLeitos")
    private TipoDaReservaDeLeito tipo;

    @ManyToOne
    @JsonIgnoreProperties("eventoLeitos")
    private MotivoDoBloqueioDeLeito motivo;

    public EventoLeito dataDoLancamento(ZonedDateTime dataDoLancamento) {
        this.dataDoLancamento = dataDoLancamento;
        return this;
    }

    public EventoLeito justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }

    public EventoLeito tipoDoEvento(TipoDoEventoLeito tipoDoEventoLeito) {
        this.tipoDoEvento = tipoDoEventoLeito;
        return this;
    }

    public EventoLeito leito(Leito leito) {
        this.leito = leito;
        return this;
    }

    public EventoLeito origem(OrigemDaReservaDeLeito origemDaReservaDeLeito) {
        this.origem = origemDaReservaDeLeito;
        return this;
    }

    public EventoLeito tipo(TipoDaReservaDeLeito tipoDaReservaDeLeito) {
        this.tipo = tipoDaReservaDeLeito;
        return this;
    }

    public EventoLeito motivo(MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito) {
        this.motivo = motivoDoBloqueioDeLeito;
        return this;
    }

}
