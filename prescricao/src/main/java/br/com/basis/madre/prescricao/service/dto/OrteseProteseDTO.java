package br.com.basis.madre.prescricao.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.OrteseProtese} entity.
 */
public class OrteseProteseDTO implements Serializable {

    private Long id;

    /**
     * descrição de procedimento especial ortese ou prótese
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "descrição de procedimento especial ortese ou prótese", required = true)
    private String decricao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrteseProteseDTO orteseProteseDTO = (OrteseProteseDTO) o;
        if (orteseProteseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orteseProteseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrteseProteseDTO{" +
            "id=" + getId() +
            ", decricao='" + getDecricao() + "'" +
            "}";
    }
}
