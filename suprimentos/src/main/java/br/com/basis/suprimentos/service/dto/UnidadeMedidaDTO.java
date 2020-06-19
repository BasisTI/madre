package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.UnidadeMedida} entity.
 */
public class UnidadeMedidaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    private String sigla;

    @NotNull
    @Size(max = 120)
    private String descricao;


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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnidadeMedidaDTO unidadeMedidaDTO = (UnidadeMedidaDTO) o;
        if (unidadeMedidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidadeMedidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnidadeMedidaDTO{" +
            "id=" + getId() +
            ", sigla='" + getSigla() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
