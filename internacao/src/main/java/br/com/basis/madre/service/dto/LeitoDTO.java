package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Integer ala;

    @NotNull
    private Integer andar;


    private Long situacaoId;

    private Long unidadeFuncionalId;

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

    public Integer getAla() {
        return ala;
    }

    public void setAla(Integer ala) {
        this.ala = ala;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }

    public Long getSituacaoId() {
        return situacaoId;
    }

    public void setSituacaoId(Long situacaoDeLeitoId) {
        this.situacaoId = situacaoDeLeitoId;
    }

    public Long getUnidadeFuncionalId() {
        return unidadeFuncionalId;
    }

    public void setUnidadeFuncionalId(Long unidadeFuncionalId) {
        this.unidadeFuncionalId = unidadeFuncionalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeitoDTO leitoDTO = (LeitoDTO) o;
        if (leitoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leitoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeitoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ala=" + getAla() +
            ", andar=" + getAndar() +
            ", situacao=" + getSituacaoId() +
            ", unidadeFuncional=" + getUnidadeFuncionalId() +
            "}";
    }
}
