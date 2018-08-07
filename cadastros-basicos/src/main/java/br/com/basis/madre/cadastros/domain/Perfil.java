package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.basis.dynamicexports.pojo.ReportObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Perfil.
 */
@Entity
@Table(name = "perfil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "perfil")
public class Perfil implements Serializable, ReportObject {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nome_perfil", length = 80, nullable = false)
    private String nomePerfil;

    @Size(max = 255)
    @Column(name = "ds_perfil", length = 255)
    private String dsPerfil;

    @OneToMany
    @JoinColumn(name="id_perfil")
    private List<PerfilFuncionalidadeAcao> perfil_funcionalidade_acao = new ArrayList<>();

    //Contrutor
    public Perfil(String nomePerfil, String dsPerfil) {
		super();
		this.nomePerfil = nomePerfil;
		this.dsPerfil = dsPerfil;
	}

    public Perfil() {
	}

    /**
     * @return the perfil_funcionalidade_acao
     */
    public List<PerfilFuncionalidadeAcao> getPerfil_funcionalidade_acao() {
        return perfil_funcionalidade_acao;
    }

    /**
     * @param perfil_funcionalidade_acao the perfil_funcionalidade_acao to set
     */
    public void setPerfil_funcionalidade_acao(List<PerfilFuncionalidadeAcao> perfil_funcionalidade_acao) {
        this.perfil_funcionalidade_acao = perfil_funcionalidade_acao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public Perfil nomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
        return this;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public String getDsPerfil() {
        return dsPerfil;
    }

    public Perfil dsPerfil(String dsPerfil) {
        this.dsPerfil = dsPerfil;
        return this;
    }

    public void setDsPerfil(String dsPerfil) {
        this.dsPerfil = dsPerfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Perfil perfil = (Perfil) o;
        if (perfil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perfil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Perfil{" +
            "id=" + getId() +
            ", nomePerfil='" + getNomePerfil() + "'" +
            ", dsPerfil='" + getDsPerfil() + "'" +
            "}";
    }
}
