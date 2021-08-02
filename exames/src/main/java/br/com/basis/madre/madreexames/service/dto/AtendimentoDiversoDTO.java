package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.AtendimentoDiverso} entity.
 */
public class AtendimentoDiversoDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer codigo;

    private Long informacoesId;

    private String informacoesCodigo;

    private Long projetoId;

    private String projetoNome;

    private Long laboratorioId;

    private String laboratorioNome;

    private Long controleId;

    private String controleCodigo;

    private Long cadaverId;

    private String cadaverNome;

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


    public Long getInformacoesId() {
        return informacoesId;
    }

    public void setInformacoesId(Long informacoesComplementaresId) {
        this.informacoesId = informacoesComplementaresId;
    }

    public String getInformacoesCodigo() {
        return informacoesCodigo;
    }

    public void setInformacoesCodigo(String informacoesComplementaresCodigo) {
        this.informacoesCodigo = informacoesComplementaresCodigo;
    }

    public Long getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Long projetoDePesquisaId) {
        this.projetoId = projetoDePesquisaId;
    }

    public String getProjetoNome() {
        return projetoNome;
    }

    public void setProjetoNome(String projetoDePesquisaNome) {
        this.projetoNome = projetoDePesquisaNome;
    }

    public Long getLaboratorioId() {
        return laboratorioId;
    }

    public void setLaboratorioId(Long laboratorioExternoId) {
        this.laboratorioId = laboratorioExternoId;
    }

    public String getLaboratorioNome() {
        return laboratorioNome;
    }

    public void setLaboratorioNome(String laboratorioExternoNome) {
        this.laboratorioNome = laboratorioExternoNome;
    }

    public Long getControleId() {
        return controleId;
    }

    public void setControleId(Long controleQualidadeId) {
        this.controleId = controleQualidadeId;
    }

    public String getControleCodigo() {
        return controleCodigo;
    }

    public void setControleCodigo(String controleQualidadeCodigo) {
        this.controleCodigo = controleQualidadeCodigo;
    }

    public Long getCadaverId() {
        return cadaverId;
    }

    public void setCadaverId(Long cadaverId) {
        this.cadaverId = cadaverId;
    }

    public String getCadaverNome() {
        return cadaverNome;
    }

    public void setCadaverNome(String cadaverNome) {
        this.cadaverNome = cadaverNome;
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
            ", informacoesId=" + getInformacoesId() +
            ", informacoesCodigo='" + getInformacoesCodigo() + "'" +
            ", projetoId=" + getProjetoId() +
            ", projetoNome='" + getProjetoNome() + "'" +
            ", laboratorioId=" + getLaboratorioId() +
            ", laboratorioNome='" + getLaboratorioNome() + "'" +
            ", controleId=" + getControleId() +
            ", controleCodigo='" + getControleCodigo() + "'" +
            ", cadaverId=" + getCadaverId() +
            ", cadaverNome='" + getCadaverNome() + "'" +
            "}";
    }
}
