package br.com.basis.madre.exames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.exames.domain.AtendimentoDiverso} entity.
 */
public class AtendimentoDiversoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer codigo;

    @NotNull
    private String descricao;


    private Long atendimentoDiversoId;

    private String atendimentoDiversoInfoClinica;

    private Long atendimentoDiversoId;

    private String atendimentoDiversoInfoClinica;

    private Long atendimentoDiversoId;

    private String atendimentoDiversoInfoClinica;

    private Long atendimentoDiversoId;

    private String atendimentoDiversoInfoClinica;

    private Long atendimentoDiversoId;

    private String atendimentoDiversoInfoClinica;
    
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

    public Long getAtendimentoDiversoId() {
        return atendimentoDiversoId;
    }

    public void setAtendimentoDiversoId(Long informacoesComplementaresId) {
        this.atendimentoDiversoId = informacoesComplementaresId;
    }

    public String getAtendimentoDiversoInfoClinica() {
        return atendimentoDiversoInfoClinica;
    }

    public void setAtendimentoDiversoInfoClinica(String informacoesComplementaresInfoClinica) {
        this.atendimentoDiversoInfoClinica = informacoesComplementaresInfoClinica;
    }

    public Long getAtendimentoDiversoId() {
        return atendimentoDiversoId;
    }

    public void setAtendimentoDiversoId(Long projetoDePesquisaId) {
        this.atendimentoDiversoId = projetoDePesquisaId;
    }

    public String getAtendimentoDiversoInfoClinica() {
        return atendimentoDiversoInfoClinica;
    }

    public void setAtendimentoDiversoInfoClinica(String projetoDePesquisaInfoClinica) {
        this.atendimentoDiversoInfoClinica = projetoDePesquisaInfoClinica;
    }

    public Long getAtendimentoDiversoId() {
        return atendimentoDiversoId;
    }

    public void setAtendimentoDiversoId(Long laboratorioExternoId) {
        this.atendimentoDiversoId = laboratorioExternoId;
    }

    public String getAtendimentoDiversoInfoClinica() {
        return atendimentoDiversoInfoClinica;
    }

    public void setAtendimentoDiversoInfoClinica(String laboratorioExternoInfoClinica) {
        this.atendimentoDiversoInfoClinica = laboratorioExternoInfoClinica;
    }

    public Long getAtendimentoDiversoId() {
        return atendimentoDiversoId;
    }

    public void setAtendimentoDiversoId(Long controleQualidadeId) {
        this.atendimentoDiversoId = controleQualidadeId;
    }

    public String getAtendimentoDiversoInfoClinica() {
        return atendimentoDiversoInfoClinica;
    }

    public void setAtendimentoDiversoInfoClinica(String controleQualidadeInfoClinica) {
        this.atendimentoDiversoInfoClinica = controleQualidadeInfoClinica;
    }

    public Long getAtendimentoDiversoId() {
        return atendimentoDiversoId;
    }

    public void setAtendimentoDiversoId(Long cadaverId) {
        this.atendimentoDiversoId = cadaverId;
    }

    public String getAtendimentoDiversoInfoClinica() {
        return atendimentoDiversoInfoClinica;
    }

    public void setAtendimentoDiversoInfoClinica(String cadaverInfoClinica) {
        this.atendimentoDiversoInfoClinica = cadaverInfoClinica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtendimentoDiversoDTO)) {
            return false;
        }

        return id != null && id.equals(((AtendimentoDiversoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtendimentoDiversoDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            ", atendimentoDiversoId=" + getAtendimentoDiversoId() +
            ", atendimentoDiversoInfoClinica='" + getAtendimentoDiversoInfoClinica() + "'" +
            ", atendimentoDiversoId=" + getAtendimentoDiversoId() +
            ", atendimentoDiversoInfoClinica='" + getAtendimentoDiversoInfoClinica() + "'" +
            ", atendimentoDiversoId=" + getAtendimentoDiversoId() +
            ", atendimentoDiversoInfoClinica='" + getAtendimentoDiversoInfoClinica() + "'" +
            ", atendimentoDiversoId=" + getAtendimentoDiversoId() +
            ", atendimentoDiversoInfoClinica='" + getAtendimentoDiversoInfoClinica() + "'" +
            ", atendimentoDiversoId=" + getAtendimentoDiversoId() +
            ", atendimentoDiversoInfoClinica='" + getAtendimentoDiversoInfoClinica() + "'" +
            "}";
    }
}
