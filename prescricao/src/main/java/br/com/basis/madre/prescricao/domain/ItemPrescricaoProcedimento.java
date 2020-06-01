package br.com.basis.madre.prescricao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.basis.madre.prescricao.domain.enumeration.TipoProcedimentoEspecial;
import lombok.Data;
import lombok.ToString;

/**
 * A ItemPrescricaoProcedimento.
 */
@Data
@Entity
@Table(name = "item_prescricao_procedimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-itemprescricaoprocedimento")
public class ItemPrescricaoProcedimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	@Field(type = FieldType.Keyword)
	private Long id;

	/**
	 * Tipo do procedimento especial
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_procedimento_especial")
	private TipoProcedimentoEspecial tipoProcedimentoEspecial;

	/**
	 * Quanditade da Ortese ou prótese, valor deve ser um inteiro
	 */
	@Min(value = 0)
	@Column(name = "quantidade_ortese_protese")
	private Integer quantidadeOrteseProtese;

	/**
	 * Informações complementares para o procedimento
	 */
	@Size(max = 255)
	@Column(name = "informacoes", length = 255)
	private String informacoes;

	/**
	 * Justificativa para o procedimento especial
	 */
	@Size(max = 255)
	@Column(name = "justificativa", length = 255)
	private String justificativa;

	/**
	 * Duração do procedimento solicitado
	 */
	@Min(value = 0)
	@Column(name = "duracao_solicitada")
	private Integer duracaoSolicitada;

	/**
	 * Chave estrangeira de tipo procedimento
	 */
	@ManyToOne
	@ToString.Exclude
	@JsonIgnoreProperties("itemPrescricaoProcedimentos")
	private TipoProcedimento tipoProcedimento;

	@ManyToOne
	@JsonIgnoreProperties("itemPrescricaoProcedimentos")
	private PrescricaoProcedimento prescricaoProcedimento;

	public TipoProcedimentoEspecial getTipoProcedimentoEspecial() {
		return tipoProcedimentoEspecial;
	}

	public ItemPrescricaoProcedimento tipoProcedimentoEspecial(TipoProcedimentoEspecial tipoProcedimentoEspecial) {
		this.tipoProcedimentoEspecial = tipoProcedimentoEspecial;
		return this;
	}

	public ItemPrescricaoProcedimento quantidadeOrteseProtese(Integer quantidadeOrteseProtese) {
		this.quantidadeOrteseProtese = quantidadeOrteseProtese;
		return this;
	}

	public ItemPrescricaoProcedimento informacoes(String informacoes) {
		this.informacoes = informacoes;
		return this;
	}

	public ItemPrescricaoProcedimento justificativa(String justificativa) {
		this.justificativa = justificativa;
		return this;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public ItemPrescricaoProcedimento duracaoSolicitada(Integer duracaoSolicitada) {
		this.duracaoSolicitada = duracaoSolicitada;
		return this;
	}

	public TipoProcedimento getTipoProcedimento() {
		return tipoProcedimento;
	}

	public ItemPrescricaoProcedimento tipoProcedimento(TipoProcedimento tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
		return this;
	}

	public PrescricaoProcedimento getPrescricaoProcedimento() {
		return prescricaoProcedimento;
	}

	public ItemPrescricaoProcedimento prescricaoProcedimento(PrescricaoProcedimento prescricaoProcedimento) {
		this.prescricaoProcedimento = prescricaoProcedimento;
		return this;
	}

}
