package br.com.basis.madre.exames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.exames.domain.Exame} entity.
 */
public class ExameDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    private String nomeUsual;

    @NotNull
    private String sigla;


    private Long exameId;

    private String exameNome;

    private Long exameId;

    private String exameNome;
    
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

    public Long getExameId() {
        return exameId;
    }

    public void setExameId(Long materialId) {
        this.exameId = materialId;
    }

    public String getExameNome() {
        return exameNome;
    }

    public void setExameNome(String materialNome) {
        this.exameNome = materialNome;
    }

    public Long getExameId() {
        return exameId;
    }

    public void setExameId(Long tipoAmostraId) {
        this.exameId = tipoAmostraId;
    }

    public String getExameNome() {
        return exameNome;
    }

    public void setExameNome(String tipoAmostraNome) {
        this.exameNome = tipoAmostraNome;
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
            ", exameId=" + getExameId() +
            ", exameNome='" + getExameNome() + "'" +
            ", exameId=" + getExameId() +
            ", exameNome='" + getExameNome() + "'" +
            "}";
    }
}
