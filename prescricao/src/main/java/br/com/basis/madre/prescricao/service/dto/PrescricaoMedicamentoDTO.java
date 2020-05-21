package br.com.basis.madre.prescricao.service.dto;

import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the
 * {@link br.com.basis.madre.prescricao.domain.PrescricaoMedicamento} entity.
 */

@NamedEntityGraph(name = "prescricaoMedicamento.itemPrescricaoMedicamentos",
attributeNodes = @NamedAttributeNode("itemPrescricaoMedicamentos")
)
public class PrescricaoMedicamentoDTO implements Serializable {

	private Long id;

	private Long idPaciente;

	@Size(max = 255)
	private String observacao;

	private Set<ItemPrescricaoMedicamentoDTO> itemPrescricaoMedicamentos = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Set<ItemPrescricaoMedicamentoDTO> getItemPrescricaoMedicamentos() {
		return itemPrescricaoMedicamentos;
	}

	public void setItemPrescricaoMedicamentos(Set<ItemPrescricaoMedicamentoDTO> itemPrescricaoMedicamentos) {
		this.itemPrescricaoMedicamentos = itemPrescricaoMedicamentos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		PrescricaoMedicamentoDTO prescricaoMedicamentoDTO = (PrescricaoMedicamentoDTO) o;
		if (prescricaoMedicamentoDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), prescricaoMedicamentoDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "PrescricaoMedicamentoDTO [id=" + id + ", idPaciente=" + idPaciente + ", observacao=" + observacao
				+ ", itemPrescricaoMedicamentos=" + itemPrescricaoMedicamentos + "]";
	}

	
}
