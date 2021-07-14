package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.Exame} entity.
 */
public class ExameDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    private String nomeUsual;

    @NotNull
    private String sigla;


    private Long materialExameId;

    private String materialExameNome;

    private Long amostraExameId;

    private String amostraExameNome;
    
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

    public String getNomeUsual() {
        return nomeUsual;
    }

    public void setNomeUsual(String nomeUsual) {
        this.nomeUsual = nomeUsual;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Long getMaterialExameId() {
        return materialExameId;
    }

    public void setMaterialExameId(Long materialId) {
        this.materialExameId = materialId;
    }

    public String getMaterialExameNome() {
        return materialExameNome;
    }

    public void setMaterialExameNome(String materialNome) {
        this.materialExameNome = materialNome;
    }

    public Long getAmostraExameId() {
        return amostraExameId;
    }

    public void setAmostraExameId(Long tipoAmostraId) {
        this.amostraExameId = tipoAmostraId;
    }

    public String getAmostraExameNome() {
        return amostraExameNome;
    }

    public void setAmostraExameNome(String tipoAmostraNome) {
        this.amostraExameNome = tipoAmostraNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExameDTO)) {
            return false;
        }

        return id != null && id.equals(((ExameDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExameDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeUsual='" + getNomeUsual() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", materialExameId=" + getMaterialExameId() +
            ", materialExameNome='" + getMaterialExameNome() + "'" +
            ", amostraExameId=" + getAmostraExameId() +
            ", amostraExameNome='" + getAmostraExameNome() + "'" +
            "}";
    }
}
