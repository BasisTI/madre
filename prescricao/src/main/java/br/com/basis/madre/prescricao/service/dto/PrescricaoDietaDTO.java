package br.com.basis.madre.prescricao.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.PrescricaoDieta}
 * entity.
 */

@Data
public class PrescricaoDietaDTO extends PrescricaoMedicaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Boolean bombaInfusao;

	private Set<ItemPrescricaoDietaDTO> itemPrescricaoDietaDTO= new HashSet<>();


}
