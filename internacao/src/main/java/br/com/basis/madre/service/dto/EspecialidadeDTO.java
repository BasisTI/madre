package br.com.basis.madre.service.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String sigla;

    @NotNull
    private String especialidade;

}
