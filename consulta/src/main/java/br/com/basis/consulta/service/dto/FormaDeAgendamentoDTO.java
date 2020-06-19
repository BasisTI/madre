package br.com.basis.consulta.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.consulta.domain.FormaDeAgendamento} entity.
 */
public class FormaDeAgendamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long numeroAutorizacao;

    @NotNull
    private String autorizacao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroAutorizacao() {
        return numeroAutorizacao;
    }

    public void setNumeroAutorizacao(Long numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
    }

    public String getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormaDeAgendamentoDTO formaDeAgendamentoDTO = (FormaDeAgendamentoDTO) o;
        if (formaDeAgendamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formaDeAgendamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormaDeAgendamentoDTO{" +
            "id=" + getId() +
            ", numeroAutorizacao=" + getNumeroAutorizacao() +
            ", autorizacao='" + getAutorizacao() + "'" +
            "}";
    }
}
