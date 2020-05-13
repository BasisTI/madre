package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MotivoDoBloqueioDeLeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

}
