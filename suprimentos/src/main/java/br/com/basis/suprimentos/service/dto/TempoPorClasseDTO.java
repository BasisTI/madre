package br.com.basis.suprimentos.service.dto;

import br.com.basis.suprimentos.domain.enumeration.TempoPorClasseTipo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TempoPorClasseDTO implements Serializable {
    private Long id;

    @NotNull
    private TempoPorClasseTipo tipo;

    @NotNull
    private Integer quantidadeClasseA;

    @NotNull
    private Integer quantidadeClasseB;

    @NotNull
    private Integer quantidadeClasseC;
}
