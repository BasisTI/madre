package br.com.basis.madre.prescricao.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * A DTO for the
 * {@link br.com.basis.madre.prescricao.domain.PrescricaoMedicamento} entity.
 */

@Data
public class PrescricaoMedicamentoDTO extends PrescricaoMedicaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<ItemPrescricaoMedicamentoDTO> itens = new HashSet<>();

}
