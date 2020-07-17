package br.com.basis.madre.prescricao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * A TipoUnidadeDieta.
 */

@Data
@Entity
@Table(name = "tipo_unidade_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-tipounidadedieta")
public class TipoUnidadeDieta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	@Field(type = FieldType.Keyword)
	private Long id;

	@NotNull
	@Size(max = 80)
	@Column(name = "descricao", length = 80, nullable = false)
	private String descricao;

	@NotNull
	@Size(max = 3)
	@Column(name = "sigla", length = 10, nullable = false)
	private String sigla;

	public TipoUnidadeDieta descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public TipoUnidadeDieta sigla(String sigla) {
		this.sigla = sigla;
		return this;
	}

}
