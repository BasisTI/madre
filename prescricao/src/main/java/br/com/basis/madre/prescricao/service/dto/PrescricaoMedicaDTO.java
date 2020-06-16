package br.com.basis.madre.prescricao.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.PrescricaoMedica} entity.
 */
public class PrescricaoMedicaDTO implements Serializable {

    private Long id;

    private Long idLeito;

    private Long idUnidadeFuncional;

    private Long idAtendimento;

    private LocalDate dataPrescricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLeito() {
        return idLeito;
    }

    public void setIdLeito(Long idLeito) {
        this.idLeito = idLeito;
    }

    public Long getIdUnidadeFuncional() {
        return idUnidadeFuncional;
    }

    public void setIdUnidadeFuncional(Long idUnidadeFuncional) {
        this.idUnidadeFuncional = idUnidadeFuncional;
    }

    public Long getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(Long idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public LocalDate getDataPrescricao() {
        return dataPrescricao;
    }

    public void setDataPrescricao(LocalDate dataPrescricao) {
        this.dataPrescricao = dataPrescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrescricaoMedicaDTO prescricaoMedicaDTO = (PrescricaoMedicaDTO) o;
        if (prescricaoMedicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prescricaoMedicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrescricaoMedicaDTO{" +
            "id=" + getId() +
            ", idLeito=" + getIdLeito() +
            ", idUnidadeFuncional=" + getIdUnidadeFuncional() +
            ", idAtendimento=" + getIdAtendimento() +
            ", dataPrescricao='" + getDataPrescricao() + "'" +
            "}";
    }
}
