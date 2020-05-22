package br.com.basis.madre.prescricao.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.PrescricaoProcedimentoEspecial} entity.
 */
public class PrescricaoProcedimentoEspecialDTO implements Serializable {

    private Long id;

    /**
     * Identificador do paciente
     */
    @NotNull
    @ApiModelProperty(value = "Identificador do paciente", required = true)
    private Long idPaciente;

    /**
     * Observação ou comentário para a prescrição de procedimento especial
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Observação ou comentário para a prescrição de procedimento especial")
    private String observacao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO = (PrescricaoProcedimentoEspecialDTO) o;
        if (prescricaoProcedimentoEspecialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prescricaoProcedimentoEspecialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrescricaoProcedimentoEspecialDTO{" +
            "id=" + getId() +
            ", idPaciente=" + getIdPaciente() +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
