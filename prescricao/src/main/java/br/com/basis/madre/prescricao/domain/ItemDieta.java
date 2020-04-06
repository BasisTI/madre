package br.com.basis.madre.prescricao.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "item_dieta")
public class ItemDieta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	private BigDecimal quantidade;

	private Integer frequencia;

	private Integer numeroVezes;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_tipo_item")
	private TipoItem tipoItem;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_tipo_unidade")
	private TipoUnidade tipoUnidade;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_tipo_aprazamento")
	private TipoAprazamento tipoAprazamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dieta", nullable = false)
	private Dieta dieta;

	public ItemDieta() {}
	
	public ItemDieta(BigDecimal quantidade, Integer frequencia, Integer numeroVezes, TipoItem tipoItem,
			TipoUnidade tipoUnidade, TipoAprazamento tipoAprazamento, Dieta dieta) {
		this.quantidade = quantidade;
		this.frequencia = frequencia;
		this.numeroVezes = numeroVezes;
		this.tipoItem = tipoItem;
		this.tipoUnidade = tipoUnidade;
		this.tipoAprazamento = tipoAprazamento;
		this.dieta = dieta;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}

	public Integer getNumeroVezes() {
		return numeroVezes;
	}

	public void setNumeroVezes(Integer numeroVezes) {
		this.numeroVezes = numeroVezes;
	}

	public TipoItem getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(TipoItem tipoItem) {
		this.tipoItem = tipoItem;
	}

	public TipoUnidade getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(TipoUnidade tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public TipoAprazamento getTipoAprazamento() {
		return tipoAprazamento;
	}

	public void setTipoAprazamento(TipoAprazamento tipoAprazamento) {
		this.tipoAprazamento = tipoAprazamento;
	}



}


