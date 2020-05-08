package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Triagem entity.
 */
public class TriagemDTO implements Serializable {

    private Long id;

    @NotNull
    private ClassificacaoDeRisco classificacaoDeRisco;

    private BigDecimal pressaoArterial;

    private BigDecimal frequenciaCardiaca;

    private BigDecimal temperatura;

    private BigDecimal peso;

    private String sinaisSintomas;

    private ZonedDateTime dataHoraDoAtendimento;


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

    public BigDecimal getPressaoArterial() {
        return pressaoArterial;
    }

    public void setPressaoArterial(BigDecimal pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    public BigDecimal getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public void setFrequenciaCardiaca(BigDecimal frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getSinaisSintomas() {
        return sinaisSintomas;
    }

    public void setSinaisSintomas(String sinaisSintomas) {
        this.sinaisSintomas = sinaisSintomas;
    }

    public ZonedDateTime getDataHoraDoAtendimento() {
        return dataHoraDoAtendimento;
    }

    public void setDataHoraDoAtendimento(ZonedDateTime dataHoraDoAtendimento) {
        this.dataHoraDoAtendimento = dataHoraDoAtendimento;
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

    public long getPaciente() {
        return pacienteId;
    }

    public void setPaciente(long pacienteId) {
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
            ", paciente=" + pacienteId +
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
