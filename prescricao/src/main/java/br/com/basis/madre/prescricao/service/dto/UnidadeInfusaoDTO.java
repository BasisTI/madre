package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.UnidadeInfusao} entity.
 */
@Data
public class UnidadeInfusaoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String descricao;

    @NotNull
    @Size(max = 10)
    private String sigla;

}
