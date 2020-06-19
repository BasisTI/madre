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
public class PrescricaoMedicamentoDTO extends PrescricaoMedicaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Set<ItemPrescricaoMedicamentoDTO> itemPrescricaoMedicamentos = new HashSet<>();

}
