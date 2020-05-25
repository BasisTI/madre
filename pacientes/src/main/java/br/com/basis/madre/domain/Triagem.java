package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;


@Entity
@Table(name = "triagem")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "triagem")
@Data
public class Triagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_de_risco", nullable = false)
    private ClassificacaoDeRisco classificacaoDeRisco;

    @Column(name = "pressao_arterial", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal pressaoArterial;

    @Column(name = "frequencia_cardiaca", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal frequenciaCardiaca;

    @Column(name = "temperatura", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal temperatura;

    @Column(name = "peso", precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal peso;

    @Column(name = "sinais_sintomas")
    @Size(min = 3, max = 255)
    private String sinaisSintomas;

    @Column(name = "data_hora_do_atendimento")
    private ZonedDateTime dataHoraDoAtendimento;

    @NotNull
    @Column(name = "descricao_queixa", nullable = false)
    @Size(min = 3, max = 255)
    private String descricaoQueixa;

    @Column(name = "vitima_de_acidente")
    private Boolean vitimaDeAcidente;

    @Column(name = "removido_de_ambulancia")
    private Boolean removidoDeAmbulancia;

    @Field(type = FieldType.Text)
    @Column(name = "Observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "preCadastroPaciente_id")
    private PreCadastroPaciente preCadastroPaciente;

    public Triagem pressaoArterial(BigDecimal pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
        return this;
    }
    public Triagem frequenciaCardiaca(BigDecimal frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
        return this;
    }

    public Triagem temperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
        return this;
    }

    public Triagem peso(BigDecimal peso) {
        this.peso = peso;
        return this;
    }

    public Triagem sinaisSintomas(String sinaisSintomas) {
        this.sinaisSintomas = sinaisSintomas;
        return this;
    }

    public Triagem dataHoraDoAtendimento(ZonedDateTime dataHoraDoAtendimento) {
        this.dataHoraDoAtendimento = dataHoraDoAtendimento;
        return this;
    }

    public Triagem descricaoQueixa(String descricaoQueixa) {
        this.descricaoQueixa = descricaoQueixa;
        return this;
    }

    public Triagem vitimaDeAcidente(Boolean vitimaDeAcidente) {
        this.vitimaDeAcidente = vitimaDeAcidente;
        return this;
    }

    public Triagem ClassificacaoDeRisco (ClassificacaoDeRisco classificacaoDeRisco) {
        this.classificacaoDeRisco = classificacaoDeRisco;
        return this;
    }

    public Triagem removidoDeAmbulancia(Boolean removidoDeAmbulancia) {
        this.removidoDeAmbulancia = removidoDeAmbulancia;
        return this;
    }

    public Triagem paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public Triagem observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public boolean isVitimaDeAcidente() {
        return vitimaDeAcidente;
    }

    public boolean isRemovidoDeAmbulancia() {
        return removidoDeAmbulancia;
    }

    public Triagem preCadastroPaciente(PreCadastroPaciente preCadastroPaciente) {
        this.preCadastroPaciente = preCadastroPaciente;
        return this;
    }
}
