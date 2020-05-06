package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "reserva_de_leito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reservadeleito")
public class ReservaDeLeito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "data_do_lancamento", nullable = false)
    private LocalDate dataDoLancamento;

    @Column(name = "justificativa")
    private String justificativa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("reservaDeLeitos")
    private Leito leito;

    @ManyToOne
    @JsonIgnoreProperties("reservaDeLeitos")
    private OrigemDaReservaDeLeito origem;

    @ManyToOne
    @JsonIgnoreProperties("reservaDeLeitos")
    private TipoDaReservaDeLeito tipo;

    public ReservaDeLeito dataDoLancamento(LocalDate dataDoLancamento) {
        this.dataDoLancamento = dataDoLancamento;
        return this;
    }

    public ReservaDeLeito justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }

    public ReservaDeLeito leito(Leito leito) {
        this.leito = leito;
        return this;
    }

    public ReservaDeLeito origem(OrigemDaReservaDeLeito origemDaReservaDeLeito) {
        this.origem = origemDaReservaDeLeito;
        return this;
    }

    public ReservaDeLeito tipo(TipoDaReservaDeLeito tipoDaReservaDeLeito) {
        this.tipo = tipoDaReservaDeLeito;
        return this;
    }

}
