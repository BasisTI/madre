package br.com.basis.madre.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Triagem entity.
 */
public class TriagemDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomeDoPaciente;

    private Long pressaoArterial;

    private Long frequenciaCardiaca;

    private Long temperatura;

    private Long peso;

    private String sinaisSintomas;

    private Instant horaDoAtendimento;

    private LocalDate dataDoAtendimento;

    private Integer idade;

    @NotNull
    private String descricaoQueixa;

    private Boolean vitimaDeAcidente;

    private Boolean removidoDeAmbulancia;

    private Long pacienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoPaciente() {
        return nomeDoPaciente;
    }

    public void setNomeDoPaciente(String nomeDoPaciente) {
        this.nomeDoPaciente = nomeDoPaciente;
    }

    public Long getPressaoArterial() {
        return pressaoArterial;
    }

    public void setPressaoArterial(Long pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    public Long getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public void setFrequenciaCardiaca(Long frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public Long getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Long temperatura) {
        this.temperatura = temperatura;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public String getSinaisSintomas() {
        return sinaisSintomas;
    }

    public void setSinaisSintomas(String sinaisSintomas) {
        this.sinaisSintomas = sinaisSintomas;
    }

    public Instant getHoraDoAtendimento() {
        return horaDoAtendimento;
    }

    public void setHoraDoAtendimento(Instant horaDoAtendimento) {
        this.horaDoAtendimento = horaDoAtendimento;
    }

    public LocalDate getDataDoAtendimento() {
        return dataDoAtendimento;
    }

    public void setDataDoAtendimento(LocalDate dataDoAtendimento) {
        this.dataDoAtendimento = dataDoAtendimento;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getDescricaoQueixa() {
        return descricaoQueixa;
    }

    public void setDescricaoQueixa(String descricaoQueixa) {
        this.descricaoQueixa = descricaoQueixa;
    }

    public Boolean isVitimaDeAcidente() {
        return vitimaDeAcidente;
    }

    public void setVitimaDeAcidente(Boolean vitimaDeAcidente) {
        this.vitimaDeAcidente = vitimaDeAcidente;
    }

    public Boolean isRemovidoDeAmbulancia() {
        return removidoDeAmbulancia;
    }

    public void setRemovidoDeAmbulancia(Boolean removidoDeAmbulancia) {
        this.removidoDeAmbulancia = removidoDeAmbulancia;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TriagemDTO triagemDTO = (TriagemDTO) o;
        if (triagemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), triagemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TriagemDTO{" +
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
            ", paciente=" + getPacienteId() +
            "}";
    }
}
