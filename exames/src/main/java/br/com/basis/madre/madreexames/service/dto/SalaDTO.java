package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.Sala} entity.
 */
public class SalaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer codigoDaSala;

    @NotNull
    private String identificacaoDaSala;

    @NotNull
    private String locacaoDaSala;

    @NotNull
    private Boolean ativo;

    private String unidadeExecutoraId;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoDaSala() {
        return codigoDaSala;
    }

    public void setCodigoDaSala(Integer codigoDaSala) {
        this.codigoDaSala = codigoDaSala;
    }

    public String getIdentificacaoDaSala() {
        return identificacaoDaSala;
    }

    public void setIdentificacaoDaSala(String identificacaoDaSala) {
        this.identificacaoDaSala = identificacaoDaSala;
    }

    public String getLocacaoDaSala() {
        return locacaoDaSala;
    }

    public void setLocacaoDaSala(String locacaoDaSala) {
        this.locacaoDaSala = locacaoDaSala;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getUnidadeExecutoraId() {
        return unidadeExecutoraId;
    }

    public void setUnidadeExecutoraId(String unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalaDTO)) {
            return false;
        }

        return id != null && id.equals(((SalaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalaDTO{" +
            "id=" + getId() +
            ", codigoDaSala=" + getCodigoDaSala() +
            ", identificacaoDaSala='" + getIdentificacaoDaSala() + "'" +
            ", locacaoDaSala='" + getLocacaoDaSala() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", unidadeExecutoraId='" + getUnidadeExecutoraId() + "'" +
            "}";
    }
}
