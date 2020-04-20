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
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "item_dieta")
public class ItemDieta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@DecimalMin(value = "0.0", inclusive = false)
	@Digits(integer = 8, fraction = 2)
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

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dieta_id", nullable = false)
	private Dieta dieta;

	public ItemDieta() {}
	
	public ItemDieta(Long id, BigDecimal quantidade, Integer frequencia, Integer numeroVezes, TipoItem tipoItem,
			TipoUnidade tipoUnidade, TipoAprazamento tipoAprazamento, Dieta dieta) 
	{
		this.id = id;
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

	public Dieta getDieta() {
		return dieta;
	}

	public void setDieta(Dieta dieta) {
		this.dieta = dieta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDieta other = (ItemDieta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	



}


