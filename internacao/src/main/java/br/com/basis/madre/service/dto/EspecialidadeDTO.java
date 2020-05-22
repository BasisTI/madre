package br.com.basis.madre.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
