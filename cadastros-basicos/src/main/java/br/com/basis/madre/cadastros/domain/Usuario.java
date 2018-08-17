package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.elasticsearch.annotations.Document;

import br.com.basis.dynamicexports.pojo.ReportObject;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "usuario")
public class Usuario implements Serializable, ReportObject {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "login", length = 60, nullable = false)
    private String login;

    @NotNull
    @Size(min = 4, max = 40)
    @Column(name = "senha", length = 40, nullable = false)
    private String senha;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @ManyToOne(optional = false)
    private Perfil perfil;

    @ManyToOne
    private Especialidade especialidade;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ta_usuario_unidade_hospitalar",
        joinColumns = @JoinColumn(name = "usuarioId", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "unidadeHospitalarId", referencedColumnName = "ID"))
    private List<UnidadeHospitalar> unidadeHospitalar = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nome, String login, String senha, String email, Boolean ativo, Perfil perfil, Especialidade especialidade) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.ativo = ativo;
        this.perfil = perfil;
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Usuario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public Usuario login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario senha(String senha) {
        this.senha = senha;
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public Usuario email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Usuario ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public Usuario perfil(Perfil perfil) {
        this.perfil = perfil;
        return this;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Usuario especialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
        return this;
    }


    public List<UnidadeHospitalar> getUnidadeHospitalar() {
        List<UnidadeHospitalar> unidadeHospitalars1;
        unidadeHospitalars1 = unidadeHospitalar;
        return unidadeHospitalars1;
    }

    public void setUnidadeHospitalar(List<UnidadeHospitalar> unidadeHospitalar) {
        List<UnidadeHospitalar> unidadeHospitalars1;
        unidadeHospitalars1 = unidadeHospitalar;
        this.unidadeHospitalar = unidadeHospitalars1;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public String getStringAtivo() {
        return (this.ativo) ? "Sim" : "Não";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        if (usuario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", login='" + getLogin() + "'" +
            ", email='" + getEmail() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", Un_hosp='" + getUnidadeHospitalar() + "'" +
            "}";
    }
}
