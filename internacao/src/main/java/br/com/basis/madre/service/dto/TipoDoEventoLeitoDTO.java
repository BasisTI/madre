package br.com.basis.madre.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TipoDoEventoLeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;
}
