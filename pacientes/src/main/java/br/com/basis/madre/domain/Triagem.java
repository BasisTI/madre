package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;


@Entity
@Table(name = "triagem")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-pacientes-triagem")
@Data
public class Triagem implements Serializable, Comparable<Triagem> {

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

    public Triagem classificacaoDeRisco (ClassificacaoDeRisco classificacaoDeRisco) {
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

    @Override
    public int compareTo(Triagem o) {
        if(this.getClassificacaoDeRisco().nivelEmergencia().compareTo(o.getClassificacaoDeRisco().nivelEmergencia()) == 0){
            ZonedDateTime thisHoraDeAtendimento = this.getDataHoraDoAtendimento() == null ? ZonedDateTime.now() : this.getDataHoraDoAtendimento();
            ZonedDateTime otherHoraDeAtendimento = o.getDataHoraDoAtendimento() == null ? ZonedDateTime.now() : o.getDataHoraDoAtendimento();
            return thisHoraDeAtendimento.compareTo(otherHoraDeAtendimento);
        }
        else{
            return this.getClassificacaoDeRisco().nivelEmergencia().compareTo(o.getClassificacaoDeRisco().nivelEmergencia());
        }
    }
}
