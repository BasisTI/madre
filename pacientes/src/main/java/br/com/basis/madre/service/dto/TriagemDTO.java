package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;

import java.math.BigDecimal;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Triagem entity.
 */
public class TriagemDTO implements Serializable {

    private Long id;

    //@NotNull
    private ClassificacaoDeRisco classificacaoDeRisco;

    private String paciente;

    //BigDecimal
    private String pressaoArterial;
    //BigDecimal
    private String frequenciaCardiaca;
    //BigDecimal
    private String temperatura;
    //BigDecimal
    private String peso;

    private String sinaisSintomas;
    //ZonedDateTime
    private String dataHoraDoAtendimento;


    @NotNull
    private String descricaoQueixa;

    private Boolean vitimaDeAcidente;

    private Boolean removidoDeAmbulancia;

    //@NotNull
    private Long pacienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassificacaoDeRisco getClassificacaoDeRisco() {
        return classificacaoDeRisco;
    }

    public void setClassificacaoDeRisco(ClassificacaoDeRisco classificacaoDeRisco) {
        this.classificacaoDeRisco = classificacaoDeRisco;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getPressaoArterial() {
        return pressaoArterial;
    }

    public void setPressaoArterial(String pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    public String getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public void setFrequenciaCardiaca(String frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getSinaisSintomas() {
        return sinaisSintomas;
    }

    public void setSinaisSintomas(String sinaisSintomas) {
        this.sinaisSintomas = sinaisSintomas;
    }

    public String getDataHoraDoAtendimento() {
        return dataHoraDoAtendimento;
    }

    public void setDataHoraDoAtendimento(String dataHoraDoAtendimento) {
        this.dataHoraDoAtendimento = dataHoraDoAtendimento;
    }

    public String getDescricaoQueixa() {
        return descricaoQueixa;
    }

    public void setDescricaoQueixa(String descricaoQueixa) {
        this.descricaoQueixa = descricaoQueixa;
    }

    public Boolean getVitimaDeAcidente() {
        return vitimaDeAcidente;
    }

    public void setVitimaDeAcidente(Boolean vitimaDeAcidente) {
        this.vitimaDeAcidente = vitimaDeAcidente;
    }

    public Boolean getRemovidoDeAmbulancia() {
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
            "id=" + id +
            ", classificacaoDeRisco=" + classificacaoDeRisco +
            ", paciente=" + paciente +
            ", pressaoArterial='" + pressaoArterial + '\'' +
            ", frequenciaCardiaca='" + frequenciaCardiaca + '\'' +
            ", temperatura='" + temperatura + '\'' +
            ", peso='" + peso + '\'' +
            ", sinaisSintomas='" + sinaisSintomas + '\'' +
            ", dataHoraDoAtendimento='" + dataHoraDoAtendimento + '\'' +
            ", descricaoQueixa='" + descricaoQueixa + '\'' +
            ", vitimaDeAcidente=" + vitimaDeAcidente +
            ", removidoDeAmbulancia=" + removidoDeAmbulancia +
            ", pacienteId=" + pacienteId +
            '}';
    }
}
