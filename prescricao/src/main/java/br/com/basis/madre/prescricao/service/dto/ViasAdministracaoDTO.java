package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.ViasAdministracao} entity.
 */

@Data
public class ViasAdministracaoDTO implements Serializable {

    private Long id;

    @Size(max = 80)
    private String descricao;

    private String sigla;

}
