package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class TipoResiduoDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 25)
    private String descricao;
}
