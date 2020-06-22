package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.CodigoCatmat} entity.
 */
public class CodigoCatmatDTO implements Serializable {

    private Long id;

    @NotNull
    private Long codigo;

    @NotNull
    private String descricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

        CodigoCatmatDTO codigoCatmatDTO = (CodigoCatmatDTO) o;
        if (codigoCatmatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), codigoCatmatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CodigoCatmatDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
