package br.com.basis.madre.prescricao.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A PrescricaoProcedimento.
 */
@Data
@Entity
@Table(name = "prescricao_procedimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-prescricaomedica", type = "prescricaomedica")
public class PrescricaoProcedimento extends PrescricaoMedica implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "prescricaoProcedimento", cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ItemPrescricaoProcedimento> itemPrescricaoProcedimentos = new HashSet<>();


	public Set<ItemPrescricaoProcedimento> getItemPrescricaoProcedimentos() {
		return itemPrescricaoProcedimentos;
	}

	public PrescricaoProcedimento itemPrescricaoProcedimentos(
			Set<ItemPrescricaoProcedimento> itemPrescricaoProcedimentos) {
		this.itemPrescricaoProcedimentos = itemPrescricaoProcedimentos;
		return this;
	}

	public PrescricaoProcedimento addItemPrescricaoProcedimento(ItemPrescricaoProcedimento itemPrescricaoProcedimento) {
		this.itemPrescricaoProcedimentos.add(itemPrescricaoProcedimento);
		itemPrescricaoProcedimento.setPrescricaoProcedimento(this);
		return this;
	}

	public PrescricaoProcedimento removeItemPrescricaoProcedimento(
			ItemPrescricaoProcedimento itemPrescricaoProcedimento) {
		this.itemPrescricaoProcedimentos.remove(itemPrescricaoProcedimento);
		itemPrescricaoProcedimento.setPrescricaoProcedimento(null);
		return this;
	}

}
