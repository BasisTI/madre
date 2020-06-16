package br.com.basis.madre.prescricao.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A PrescricaoMedicamento.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prescricao_medicamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-prescricaomedicamento")

public class PrescricaoMedicamento extends PrescricaoMedica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_paciente")
	private Long idPaciente;

	@Size(max = 255)
	@Column(name = "observacao", length = 255)
	private String observacao;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "prescricaoMedicamento", cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos = new HashSet<>();

	public PrescricaoMedicamento idPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
		return this;
	}

	public PrescricaoMedicamento observacao(String observacao) {
		this.observacao = observacao;
		return this;
	}

	public PrescricaoMedicamento itemPrescricaoMedicamentos(Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos) {
		this.itemPrescricaoMedicamentos = itemPrescricaoMedicamentos;
		return this;
	}

	public PrescricaoMedicamento addItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
		this.itemPrescricaoMedicamentos.add(itemPrescricaoMedicamento);
		itemPrescricaoMedicamento.setPrescricaoMedicamento(this);
		return this;
	}

	public PrescricaoMedicamento removeItemPrescricaoMedicamento(ItemPrescricaoMedicamento itemPrescricaoMedicamento) {
		this.itemPrescricaoMedicamentos.remove(itemPrescricaoMedicamento);
		itemPrescricaoMedicamento.setPrescricaoMedicamento(null);
		return this;
	}

}
