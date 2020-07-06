package br.com.basis.madre.prescricao.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A PrescricaoDieta.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prescricao_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-prescricaomedica", type="prescricaomedica")
public class PrescricaoDieta extends PrescricaoMedica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "bomba_infusao")
	private Boolean bombaInfusao;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "prescricaoDieta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ItemPrescricaoDieta> itemPrescricaoDietas = new HashSet<>();


	public PrescricaoDieta itemPrescricaoDietas(Set<ItemPrescricaoDieta> itemPrescricaoDietas) {
		this.itemPrescricaoDietas = itemPrescricaoDietas;
		return this;
	}

	public PrescricaoDieta addItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
		this.itemPrescricaoDietas.add(itemPrescricaoDieta);
		itemPrescricaoDieta.setPrescricaoDieta(this);
		return this;
	}

	public PrescricaoDieta removeItemPrescricaoDieta(ItemPrescricaoDieta itemPrescricaoDieta) {
		this.itemPrescricaoDietas.remove(itemPrescricaoDieta);
		itemPrescricaoDieta.setPrescricaoDieta(null);
		return this;
	}

}
