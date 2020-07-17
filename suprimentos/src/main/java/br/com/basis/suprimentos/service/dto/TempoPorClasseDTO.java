package br.com.basis.suprimentos.service.dto;

import br.com.basis.suprimentos.domain.enumeration.TipoTempoPorClasse;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TempoPorClasseDTO implements Serializable {
    private Long id;

    @NotNull
    private TipoTempoPorClasse tipo;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    private Integer quantidadeClasseA;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    private Integer quantidadeClasseB;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    private Integer quantidadeClasseC;

    private Long almoxarifadoId;
}
