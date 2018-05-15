package br.com.basis.madre.cadastros.domain;

import br.com.basis.dynamicexports.pojo.ReportObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

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
    @Size(min = 1, max = 80)
    @Column(name = "perfil", length = 80, nullable = false)
    private String perfil;

    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "unidade_de_saude", length = 80, nullable = false)
    private String unidadeDeSaude;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Usuario login(String login) {
        this.login = login;
        return this;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario senha(String senha) {
        this.senha = senha;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario email(String email) {
        this.email = email;
        return this;
    }

    public String getPerfil() {
        return this.perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Usuario perfil(String perfil) {
        this.perfil = perfil;
        return this;
    }

    public String getUnidadeDeSaude() {
        return unidadeDeSaude;
    }

    public void setUnidadeDeSaude(String unidadeDeSaude) {
        this.unidadeDeSaude = unidadeDeSaude;
    }

    public Usuario unidadeDeSaude(String unidadeDeSaude) {
        this.unidadeDeSaude = unidadeDeSaude;
        return this;
    }

    public Boolean isAtivo() {
        return this.ativo;
    }

    public Usuario ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getStringAtivo() {
        return (this.ativo) ? "Sim" : "Não";
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
            ", perfil='" + getPerfil() + "'" +
            ", unidadeDeSaude='" + getUnidadeDeSaude() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
