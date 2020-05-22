package br.com.basis.madre.prescricao.service.dto;
import javax.validation.constraints.*;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

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
