package br.com.basis.madre.prescricao.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import lombok.Data;

/**
 * A DTO for the
 * {@link br.com.basis.madre.prescricao.domain.PrescricaoMedicamento} entity.
 */

@Data
public class PrescricaoMedicamentoDTO implements Serializable {

	private Long id;

	private Long idPaciente;

	@Size(max = 255)
	private String observacao;

	private Set<ItemPrescricaoMedicamentoDTO> itemPrescricaoMedicamentos = new HashSet<>();

	
}
