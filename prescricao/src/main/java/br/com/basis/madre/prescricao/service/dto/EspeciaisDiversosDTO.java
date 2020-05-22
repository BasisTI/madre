package br.com.basis.madre.prescricao.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.EspeciaisDiversos} entity.
 */
public class EspeciaisDiversosDTO implements Serializable {

    private Long id;

    /**
     * descrição de procedimentos especiais diversos
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "descrição de procedimentos especiais diversos", required = true)
    private String descricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        EspeciaisDiversosDTO especiaisDiversosDTO = (EspeciaisDiversosDTO) o;
        if (especiaisDiversosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), especiaisDiversosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EspeciaisDiversosDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
