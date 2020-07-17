package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ItemNotaRecebimentoDTO implements Serializable {
    private Long id;

    @NotNull
    @Min(value = 0L)
    @Max(value = 255L)
    private Long quantidadeReceber;

    @NotNull
    @Size(max = 255)
    private String quantidadeConvertida;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valorTotal;

    private Long recebimentoId;

    private Long marcaComercialId;

    private Long materialId;

    private Long unidadeMedidaId;
}
