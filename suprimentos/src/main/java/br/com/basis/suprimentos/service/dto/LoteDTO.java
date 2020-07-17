package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class LoteDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(min = 1, max = 120)
    private String descricao;

    @Size(min = 1, max = 15)
    private String serie;

    @Min(value = 0L)
    private Long quantidadeDisponivel;

    @Min(value = 0L)
    private Long quantidadeBloqueada;

    @Min(value = 0L)
    private Long quantidadeProblema;

    @NotNull
    private LocalDate dataValidade;

    private Long marcaComercialId;

    private Long estoqueId;
}
