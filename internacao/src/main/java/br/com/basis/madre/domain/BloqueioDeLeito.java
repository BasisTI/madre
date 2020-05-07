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
@Table(name = "bloqueio_de_leito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bloqueiodeleito")
public class BloqueioDeLeito implements Serializable {

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
    @JsonIgnoreProperties("bloqueioDeLeitos")
    private Leito leito;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("bloqueioDeLeitos")
    private MotivoDoBloqueioDeLeito motivo;

    public BloqueioDeLeito dataDoLancamento(LocalDate dataDoLancamento) {
        this.dataDoLancamento = dataDoLancamento;
        return this;
    }

    public BloqueioDeLeito justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }

    public BloqueioDeLeito leito(Leito leito) {
        this.leito = leito;
        return this;
    }

    public BloqueioDeLeito motivo(MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito) {
        this.motivo = motivoDoBloqueioDeLeito;
        return this;
    }

}
