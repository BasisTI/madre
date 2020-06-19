package br.com.basis.consulta.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.consulta.domain.CondicaoDeAtendimento} entity.
 */
public class CondicaoDeAtendimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String sigla;

    @NotNull
    private String especialidade;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO = (CondicaoDeAtendimentoDTO) o;
        if (condicaoDeAtendimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), condicaoDeAtendimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CondicaoDeAtendimentoDTO{" +
            "id=" + getId() +
            ", sigla='" + getSigla() + "'" +
            ", especialidade='" + getEspecialidade() + "'" +
            "}";
    }
}
