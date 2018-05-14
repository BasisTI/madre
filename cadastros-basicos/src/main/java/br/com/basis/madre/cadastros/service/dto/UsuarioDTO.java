package br.com.basis.madre.cadastros.service.dto;

import br.com.basis.dynamicexports.pojo.ReportObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import br.com.basis.madre.cadastros.domain.Usuario;
public class UsuarioDTO implements ReportObject, Serializable {

    /**
     * A Usuario.
     */
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String nome;

    @NotNull
    @Size(min = 1, max = 60)
    private String login;

    @NotNull
    @Size(min = 4, max = 40)
    private String senha;

    @NotNull
    @Size(min = 3, max = 100)
    private String email;

    @NotNull
    @Size(min = 1, max = 80)
    private String perfil;

        @NotNull
        @Size(min = 1, max = 80)
        private String unidade_de_saude;

        @NotNull
        private Boolean ativo;

        // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPerfil() {
            return perfil;
        }

        public void setPerfil(String perfil) {
            this.perfil = perfil;
        }

        public String getUnidade_de_saude() {
            return unidade_de_saude;
        }

        public void setUnidade_de_saude(String unidade_de_saude) {
            this.unidade_de_saude = unidade_de_saude;
        }

        public Boolean isAtivo() {
            return ativo;
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
            Usuario usuario = (br.com.basis.madre.cadastros.domain.Usuario) o;
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
                ", unidade_de_saude='" + getUnidade_de_saude() + "'" +
                ", ativo='" + isAtivo() + "'" +
                "}";
        }
}


