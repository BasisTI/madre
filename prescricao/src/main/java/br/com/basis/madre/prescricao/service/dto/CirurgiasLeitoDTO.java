package br.com.basis.madre.prescricao.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.CirurgiasLeito} entity.
 */
public class CirurgiasLeitoDTO implements Serializable {

    private Long id;

    /**
     * descrição de procedimentos especiais de cirurgias no leito
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "descrição de procedimentos especiais de cirurgias no leito", required = true)
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

        CirurgiasLeitoDTO cirurgiasLeitoDTO = (CirurgiasLeitoDTO) o;
        if (cirurgiasLeitoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cirurgiasLeitoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CirurgiasLeitoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
