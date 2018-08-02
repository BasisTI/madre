package br.com.basis.madre.cadastros.domain;

import java.util.List;

public class RelUsuarioUnidadehospitalarDTO {

    private Long id;

    private String nome;

    private String login;

    private Especialidade especialidade;

    private List<UnidadeHospitalar> unidHospitalares;

    private String senha;

    private String email;

    private Boolean ativo;

    private Perfil perfil;

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public List<UnidadeHospitalar> getUnidHospitalares() {
        return unidHospitalares;
    }

    public void setUnidHospitalares(List<UnidadeHospitalar> unidHospitalares) {
        this.unidHospitalares = unidHospitalares;
    }

    @Override
    public String toString() {
        return "RelUsuarioUnidadehospitalarDTO{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", login='" + login + '\'' +
            ", senha='" + senha + '\'' +
            ", email='" + email + '\'' +
            ", ativo=" + ativo +
            ", perfil=" + perfil +
            ", especialidade=" + especialidade +
            ", unidHospitalares=" +  +
            '}';
    }

}
