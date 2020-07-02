package br.com.basis.madre.prescricao.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "madre-prescricao-prescricaomedicamento", type = "prescricaomedicamento")
public class PrescricaoMedicamento extends PrescricaoMedica implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "prescricaoMedicamento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@Field(type = FieldType.Nested)
	private Set<ItemPrescricaoMedicamento> itemPrescricaoMedicamentos = new HashSet<>();

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
