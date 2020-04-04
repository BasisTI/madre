package br.com.basis.madre.prescricao.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "dieta")
public class Dieta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;
	
	@Column(name = "id_paciente")
	private Long idPaciente;
	
	private String observacao;
	
	@Column(name = "bomba_infusao")
	private Boolean bombaInfusao;
	
	@JsonIgnoreProperties("item_dieta")
	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemDieta> itemDieta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getBombaInfusao() {
		return bombaInfusao;
	}

	public void setBombaInfusao(Boolean bombaInfusao) {
		this.bombaInfusao = bombaInfusao;
	}



	public List<ItemDieta> getItemDieta() {
		return itemDieta;
	}

	public void setItemDieta(List<ItemDieta> itemDieta) {
		this.itemDieta = itemDieta;
	}
	
	public Dieta () {}

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
		Dieta other = (Dieta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}


