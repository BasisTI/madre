package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import java.util.Objects;


@Entity
@Table(name = "triagem")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "triagem")
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
    //@Size(min = 3, max = 255)
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
    //@NotNull
    @JsonIgnoreProperties("")
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPressaoArterial() {
        return pressaoArterial;
    }

    public Triagem pressaoArterial(BigDecimal pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
        return this;
    }

    public void setPressaoArterial(BigDecimal pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    public BigDecimal getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public Triagem frequenciaCardiaca(BigDecimal frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
        return this;
    }

    public void setFrequenciaCardiaca(BigDecimal frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public Triagem temperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
        return this;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public Triagem peso(BigDecimal peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getSinaisSintomas() {
        return sinaisSintomas;
    }

    public Triagem sinaisSintomas(String sinaisSintomas) {
        this.sinaisSintomas = sinaisSintomas;
        return this;
    }

    public void setSinaisSintomas(String sinaisSintomas) {
        this.sinaisSintomas = sinaisSintomas;
    }

    public ZonedDateTime getDataHoraDoAtendimento() {
        return dataHoraDoAtendimento;
    }

    public Triagem dataHoraDoAtendimento(ZonedDateTime dataHoraDoAtendimento) {
        this.dataHoraDoAtendimento = dataHoraDoAtendimento;
        return this;
    }

    public void setDataHoraDoAtendimento(ZonedDateTime dataHoraDoAtendimento) {
        this.dataHoraDoAtendimento = dataHoraDoAtendimento;
    }

    public String getDescricaoQueixa() {
        return descricaoQueixa;
    }

    public Triagem descricaoQueixa(String descricaoQueixa) {
        this.descricaoQueixa = descricaoQueixa;
        return this;
    }

    public void setDescricaoQueixa(String descricaoQueixa) {
        this.descricaoQueixa = descricaoQueixa;
    }

    public Boolean isVitimaDeAcidente() {
        return vitimaDeAcidente;
    }

    public Triagem vitimaDeAcidente(Boolean vitimaDeAcidente) {
        this.vitimaDeAcidente = vitimaDeAcidente;
        return this;
    }

    public ClassificacaoDeRisco getClassificacaoDeRisco() {
        return classificacaoDeRisco;
    }

    public void setClassificacaoDeRisco(ClassificacaoDeRisco classificacaoDeRisco) {
        this.classificacaoDeRisco = classificacaoDeRisco;
    }

    public Triagem ClassificacaoDeRisco (ClassificacaoDeRisco classificacaoDeRisco) {
        this.classificacaoDeRisco = classificacaoDeRisco;
        return this;
    }

    public void setVitimaDeAcidente(Boolean vitimaDeAcidente) {
        this.vitimaDeAcidente = vitimaDeAcidente;
    }

    public Boolean isRemovidoDeAmbulancia() {
        return removidoDeAmbulancia;
    }

    public Triagem removidoDeAmbulancia(Boolean removidoDeAmbulancia) {
        this.removidoDeAmbulancia = removidoDeAmbulancia;
        return this;
    }

    public void setRemovidoDeAmbulancia(Boolean removidoDeAmbulancia) {
        this.removidoDeAmbulancia = removidoDeAmbulancia;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Triagem paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getObservacao() {
        return observacao;
    }

    public Triagem observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Triagem triagem = (Triagem) o;
        if (triagem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), triagem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Triagem{" +
            "id=" + getId() +
            ", classificacaoDeRisco=" + getClassificacaoDeRisco() +
            ", pressaoArterial=" + getPressaoArterial() +
            ", frequenciaCardiaca=" + getFrequenciaCardiaca() +
            ", temperatura=" + getTemperatura() +
            ", peso=" + getPeso() +
            ", sinaisSintomas='" + getSinaisSintomas() + "'" +
            ", dataHoraDoAtendimento='" + getDataHoraDoAtendimento() + "'" +
            ", descricaoQueixa='" + getDescricaoQueixa() + "'" +
            ", vitimaDeAcidente='" + isVitimaDeAcidente() + "'" +
            ", removidoDeAmbulancia='" + isRemovidoDeAmbulancia() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
