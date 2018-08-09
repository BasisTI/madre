package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PerfilDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nomePerfil;
    private String dsPerfil;

    private List<AcaoTemp> acaoTemp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AcaoTemp> getacaoTemp() {
        List<AcaoTemp> acaoTemps1;
        acaoTemps1 = acaoTemp;
        return acaoTemps1;
    }

    public void setacaoTemp(List<AcaoTemp> acaoTemp) {
        List<AcaoTemp> acaoTemps1;
        acaoTemps1 = acaoTemp;
        this.acaoTemp = acaoTemps1;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public String getDsPerfil() {
        return dsPerfil;
    }

    public void setDsPerfil(String dsPerfil) {
        this.dsPerfil = dsPerfil;
    }

    @Override
    public String toString() {
        return "PerfilDTO [nomePerfil=" + nomePerfil + ", dsPerfil=" + dsPerfil + ", acaoTemp=" + acaoTemp + "]";
    }

}
