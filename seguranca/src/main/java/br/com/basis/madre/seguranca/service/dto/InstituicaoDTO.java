package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Instituicao} entity.
 */
public class InstituicaoDTO implements Serializable {

    private Long id;

    private Integer codigo;

    @NotNull
    private String descricao;

    @NotNull
    private Boolean interno;


    private Long conselhosProfissionaisId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isInterno() {
        return interno;
    }

    public void setInterno(Boolean interno) {
        this.interno = interno;
    }

    public Long getConselhosProfissionaisId() {
        return conselhosProfissionaisId;
    }

    public void setConselhosProfissionaisId(Long conselhosProfissionaisId) {
        this.conselhosProfissionaisId = conselhosProfissionaisId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstituicaoDTO)) {
            return false;
        }

        return id != null && id.equals(((InstituicaoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstituicaoDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            ", interno='" + isInterno() + "'" +
            ", conselhosProfissionaisId=" + getConselhosProfissionaisId() +
            "}";
    }
}
