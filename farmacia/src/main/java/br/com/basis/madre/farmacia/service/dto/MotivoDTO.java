package br.com.basis.madre.farmacia.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.farmacia.domain.Motivo} entity.
 */
public class MotivoDTO implements Serializable {

    private Long id;

    private String codigo;

    private String descricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
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

        MotivoDTO motivoDTO = (MotivoDTO) o;
        if (motivoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), motivoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MotivoDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
