package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Funcionalidade.
 */
@Entity
@Table(name = "funcionalidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "funcionalidade")
public class Funcionalidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@NotNull
	@Size(max = 20)
	@Column(name = "nm_funcionalidade", length = 20, nullable = false)
	private String nm_funcionalidade;

	@NotNull
	@Size(max = 20)
	@Column(name = "cd_funcionalidade", length = 20, nullable = false)
	private String cd_funcionalidade;

	@NotNull
	@Size(max = 1)
	@Column(name = "st_excluido", length = 1, nullable = false)
	private String st_excluido;

	@ManyToMany//(mappedBy = "funcionalidades")
	@JoinTable(name = "funcionalidade_acao",
	joinColumns = @JoinColumn(name = "funcionalidade_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "acao_id", referencedColumnName = "id"))

	private List<Acao> acaoList; //= new ArrayList<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove

	public List<Acao> getAcaoList() {
		return acaoList;
	}
	
	public void setAcaoList(List<Acao> acaoList) {
		this.acaoList = acaoList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNm_funcionalidade() {
		return nm_funcionalidade;
	}

	public Funcionalidade nm_funcionalidade(String nm_funcionalidade) {
		this.nm_funcionalidade = nm_funcionalidade;
		return this;
	}

	public void setNm_funcionalidade(String nm_funcionalidade) {
		this.nm_funcionalidade = nm_funcionalidade;
	}

	public String getCd_funcionalidade() {
		return cd_funcionalidade;
	}

	public Funcionalidade cd_funcionalidade(String cd_funcionalidade) {
		this.cd_funcionalidade = cd_funcionalidade;
		return this;
	}

	public void setCd_funcionalidade(String cd_funcionalidade) {
		this.cd_funcionalidade = cd_funcionalidade;
	}

	public String getSt_excluido() {
		return st_excluido;
	}

	public Funcionalidade st_excluido(String st_excluido) {
		this.st_excluido = st_excluido;
		return this;
	}

	public void setSt_excluido(String st_excluido) {
		this.st_excluido = st_excluido;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Funcionalidade funcionalidade = (Funcionalidade) o;
		if (funcionalidade.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), funcionalidade.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Funcionalidade{" + "id=" + getId() + ", nm_funcionalidade='" + getNm_funcionalidade() + "'"
				+ ", cd_funcionalidade='" + getCd_funcionalidade() + "'" + ", st_excluido='" + getSt_excluido() + "'"
				+ "}";
	}
}
