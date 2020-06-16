package br.com.basis.madre.prescricao.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A PrescricaoMedica.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prescricao_medica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-prescricaomedica")
public class PrescricaoMedica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	@Field(type = FieldType.Keyword)
	private Long id;

	@Column(name = "id_leito")
	private Long idLeito;

	@Column(name = "id_unidade_funcional")
	private Long idUnidadeFuncional;

	@Column(name = "id_atendimento")
	private Long idAtendimento;

	@Column(name = "data_prescricao")
	private LocalDate dataPrescricao;

	public PrescricaoMedica idLeito(Long idLeito) {
		this.idLeito = idLeito;
		return this;
	}

	public PrescricaoMedica idUnidadeFuncional(Long idUnidadeFuncional) {
		this.idUnidadeFuncional = idUnidadeFuncional;
		return this;
	}

	public PrescricaoMedica idAtendimento(Long idAtendimento) {
		this.idAtendimento = idAtendimento;
		return this;
	}

	public PrescricaoMedica dataPrescricao(LocalDate dataPrescricao) {
		this.dataPrescricao = dataPrescricao;
		return this;
	}

}
