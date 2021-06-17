package br.com.basis.madre.exames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.exames.domain.Sinonimo} entity.
 */
public class SinonimoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    private Boolean situacao;


    private Long sinonimoId;

    private String sinonimoNome;
    
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

    public Boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Long getSinonimoId() {
        return sinonimoId;
    }

    public void setSinonimoId(Long exameId) {
        this.sinonimoId = exameId;
    }

    public String getSinonimoNome() {
        return sinonimoNome;
    }

    public void setSinonimoNome(String exameNome) {
        this.sinonimoNome = exameNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SinonimoDTO)) {
            return false;
        }

        return id != null && id.equals(((SinonimoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SinonimoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", situacao='" + isSituacao() + "'" +
            ", sinonimoId=" + getSinonimoId() +
            ", sinonimoNome='" + getSinonimoNome() + "'" +
            "}";
    }
}
