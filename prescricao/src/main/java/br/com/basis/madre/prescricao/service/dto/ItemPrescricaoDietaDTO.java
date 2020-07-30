package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoDieta} entity.
 */
@Data
public class ItemPrescricaoDietaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

    @DecimalMin(value = "0")
    private BigDecimal quantidade;

    @Min(value = 0)
    private Integer frequencia;

    private Integer numeroVezes;

    private TipoItemDietaDTO tipoItemDieta;

    private TipoAprazamentoDTO tipoAprazamento;

    private TipoUnidadeDietaDTO tipoUnidadeDieta;
    
}
