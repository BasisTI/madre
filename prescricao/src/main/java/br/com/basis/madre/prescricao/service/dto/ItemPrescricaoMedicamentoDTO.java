package br.com.basis.madre.prescricao.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.basis.madre.prescricao.domain.enumeration.UnidadeTempo;
import lombok.Data;

/**
 * A DTO for the
 * {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento}
 * entity.
 */
@Data
public class ItemPrescricaoMedicamentoDTO implements Serializable {

	private Long id;

	@NotNull
	private Long idMedicamento;

	private Long idListaMedicamentos;

	@NotNull
	@DecimalMin(value = "0")
	private BigDecimal dose;

	@Min(value = 0)
	private Integer frequencia;

	private Boolean todasVias;

	private Boolean bombaInfusao;

	@DecimalMin(value = "0")
	private BigDecimal velocidadeInfusao;

	@Min(value = 0)
	private Integer tempoInfusao;

	private UnidadeTempo unidadeTempo;

	private LocalDate inicioAdministracao;

	private Boolean condicaoNecessaria;

	@Size(max = 255)
	private String observacaoCondicao;

	private Long viasAdministracaoId;

	private Long diluenteId;

	private Long unidadeInfusaoId;

	private Long unidadeDoseId;

	private Long tipoAprazamentoId;


}
