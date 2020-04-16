package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Triagem.
 */
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
    @Column(name = "nome_do_paciente", nullable = false)
    private String nomeDoPaciente;

    @Column(name = "pressao_arterial")
    private Long pressaoArterial;

    @Column(name = "frequencia_cardiaca")
    private Long frequenciaCardiaca;

    @Column(name = "temperatura")
    private Long temperatura;

    @Column(name = "peso")
    private Long peso;

    @Column(name = "sinais_sintomas")
    private String sinaisSintomas;

    @Column(name = "hora_do_atendimento")
    private Instant horaDoAtendimento;

    @Column(name = "data_do_atendimento")
    private LocalDate dataDoAtendimento;

    @Column(name = "idade")
    private Integer idade;

    @NotNull
    @Column(name = "descricao_queixa", nullable = false)
    private String descricaoQueixa;

    @Column(name = "vitima_de_acidente")
    private Boolean vitimaDeAcidente;

    @Column(name = "removido_de_ambulancia")
    private Boolean removidoDeAmbulancia;

    @ManyToOne
    @JsonIgnoreProperties("triagem")
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoPaciente() {
        return nomeDoPaciente;
    }

    public Triagem nomeDoPaciente(String nomeDoPaciente) {
        this.nomeDoPaciente = nomeDoPaciente;
        return this;
    }

    public void setNomeDoPaciente(String nomeDoPaciente) {
        this.nomeDoPaciente = nomeDoPaciente;
    }

    public Long getPressaoArterial() {
        return pressaoArterial;
    }

    public Triagem pressaoArterial(Long pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
        return this;
    }

    public void setPressaoArterial(Long pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    public Long getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public Triagem frequenciaCardiaca(Long frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
        return this;
    }

    public void setFrequenciaCardiaca(Long frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public Long getTemperatura() {
        return temperatura;
    }

    public Triagem temperatura(Long temperatura) {
        this.temperatura = temperatura;
        return this;
    }

    public void setTemperatura(Long temperatura) {
        this.temperatura = temperatura;
    }

    public Long getPeso() {
        return peso;
    }

    public Triagem peso(Long peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Long peso) {
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

    public Instant getHoraDoAtendimento() {
        return horaDoAtendimento;
    }

    public Triagem horaDoAtendimento(Instant horaDoAtendimento) {
        this.horaDoAtendimento = horaDoAtendimento;
        return this;
    }

    public void setHoraDoAtendimento(Instant horaDoAtendimento) {
        this.horaDoAtendimento = horaDoAtendimento;
    }

    public LocalDate getDataDoAtendimento() {
        return dataDoAtendimento;
    }

    public Triagem dataDoAtendimento(LocalDate dataDoAtendimento) {
        this.dataDoAtendimento = dataDoAtendimento;
        return this;
    }

    public void setDataDoAtendimento(LocalDate dataDoAtendimento) {
        this.dataDoAtendimento = dataDoAtendimento;
    }

    public Integer getIdade() {
        return idade;
    }

    public Triagem idade(Integer idade) {
        this.idade = idade;
        return this;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
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
            ", nomeDoPaciente='" + getNomeDoPaciente() + "'" +
            ", pressaoArterial=" + getPressaoArterial() +
            ", frequenciaCardiaca=" + getFrequenciaCardiaca() +
            ", temperatura=" + getTemperatura() +
            ", peso=" + getPeso() +
            ", sinaisSintomas='" + getSinaisSintomas() + "'" +
            ", horaDoAtendimento='" + getHoraDoAtendimento() + "'" +
            ", dataDoAtendimento='" + getDataDoAtendimento() + "'" +
            ", idade=" + getIdade() +
            ", descricaoQueixa='" + getDescricaoQueixa() + "'" +
            ", vitimaDeAcidente='" + isVitimaDeAcidente() + "'" +
            ", removidoDeAmbulancia='" + isRemovidoDeAmbulancia() + "'" +
            "}";
    }
}
