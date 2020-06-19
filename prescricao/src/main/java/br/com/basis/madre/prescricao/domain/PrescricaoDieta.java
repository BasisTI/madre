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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.logstash.logback.encoder.org.apache.commons.lang3.builder.ToStringExclude;

/**
 * A PrescricaoDieta.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "prescricao_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-prescricaodieta")
public class PrescricaoDieta extends PrescricaoMedica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "bomba_infusao")
	private Boolean bombaInfusao;


	@ToStringExclude
	@OneToMany(mappedBy = "prescricaoDieta", cascade = CascadeType.ALL)
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
