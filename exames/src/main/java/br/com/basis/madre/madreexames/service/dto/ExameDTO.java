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

    @NotNull
    private Boolean ativo;

    @NotNull
    private Boolean impressao;

    @NotNull
    private Boolean consisteInterfaceamento;

    @NotNull
    private Boolean anexaDocumentos;


    private Long materialExameId;

    private String materialExameNome;
    
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

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isImpressao() {
        return impressao;
    }

    public void setImpressao(Boolean impressao) {
        this.impressao = impressao;
    }

    public Boolean isConsisteInterfaceamento() {
        return consisteInterfaceamento;
    }

    public void setConsisteInterfaceamento(Boolean consisteInterfaceamento) {
        this.consisteInterfaceamento = consisteInterfaceamento;
    }

    public Boolean isAnexaDocumentos() {
        return anexaDocumentos;
    }

    public void setAnexaDocumentos(Boolean anexaDocumentos) {
        this.anexaDocumentos = anexaDocumentos;
    }

    public Long getMaterialExameId() {
        return materialExameId;
    }

    public void setMaterialExameId(Long materialDeExameId) {
        this.materialExameId = materialDeExameId;
    }

    public String getMaterialExameNome() {
        return materialExameNome;
    }

    public void setMaterialExameNome(String materialDeExameNome) {
        this.materialExameNome = materialDeExameNome;
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
            ", ativo='" + isAtivo() + "'" +
            ", impressao='" + isImpressao() + "'" +
            ", consisteInterfaceamento='" + isConsisteInterfaceamento() + "'" +
            ", anexaDocumentos='" + isAnexaDocumentos() + "'" +
            ", materialExameId=" + getMaterialExameId() +
            ", materialExameNome='" + getMaterialExameNome() + "'" +
            "}";
    }
}
