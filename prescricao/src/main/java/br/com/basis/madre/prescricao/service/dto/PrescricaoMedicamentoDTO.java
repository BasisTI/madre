package br.com.basis.madre.prescricao.service.dto;

import java.io.Serializable;
import java.util.Set;

public class PrescricaoMedicamentoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPaciente;

	private String observacao;
	
	private Set<ItemPrescricaoMedicamentoDTO> itemPrescricaoMedicamentoDTOs;


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

	public Set<ItemPrescricaoMedicamentoDTO> getItemPrescricaoMedicamentoDTOs() {
		return itemPrescricaoMedicamentoDTOs;
	}

	public void setItemPrescricaoMedicamentoDTOs(Set<ItemPrescricaoMedicamentoDTO> itemPrescricaoMedicamentoDTOs) {
		this.itemPrescricaoMedicamentoDTOs = itemPrescricaoMedicamentoDTOs;
	}

	

}
