package br.com.basis.madre.prescricao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * A ItemPrescricaoDiagnostico.
 */
@Data
@Entity
@Table(name = "item_prescricao_diagnostico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "itemprescricaodiagnostico")
public class ItemPrescricaoDiagnostico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	@Field(type = FieldType.Keyword)
	private Long id;

	@NotNull
	@Column(name = "id_cid", nullable = false)
	private Long idCid;

	@Size(max = 255)
	@Column(name = "complemento", length = 255)
	private String complemento;

	@Field(type = FieldType.Object)
	@Transient
	private CID cid;

	@NotNull
	@ManyToOne
	@JsonIgnoreProperties("itemPrescricaoDiagnosticos")
	private PrescricaoDiagnostico prescricaoDiagnostico;

	public ItemPrescricaoDiagnostico idCid(Long idCid) {
		this.idCid = idCid;
		return this;
	}

	public ItemPrescricaoDiagnostico complemento(String complemento) {
		this.complemento = complemento;
		return this;
	}

	public ItemPrescricaoDiagnostico prescricaoDiagnostico(PrescricaoDiagnostico prescricaoDiagnostico) {
		this.prescricaoDiagnostico = prescricaoDiagnostico;
		return this;
	}

}
