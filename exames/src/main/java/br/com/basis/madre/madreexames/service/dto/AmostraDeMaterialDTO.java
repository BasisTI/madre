package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import br.com.basis.madre.madreexames.domain.enumeration.OrigemTipoAmostra;
import br.com.basis.madre.madreexames.domain.enumeration.UnidadeDeMedida;
import br.com.basis.madre.madreexames.domain.enumeration.Responsavel;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.AmostraDeMaterial} entity.
 */
public class AmostraDeMaterialDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private OrigemTipoAmostra origem;

    @NotNull
    private Integer numeroDeAmostras;

    private Integer volumeDaAmostra;

    @NotNull
    private UnidadeDeMedida unidadeDeMedida;

    @NotNull
    private Responsavel responsavel;

    @NotNull
    private Boolean congelado;

    private Integer unidadeFuncionalId;


    private Long amostraRecipienteId;

    private String amostraRecipienteNome;

    private Long amostraAnticoagulanteId;

    private String amostraAnticoagulanteNome;

    private Long amostraMaterialId;

    private String amostraMaterialNome;

    private Long materialDeExameId;

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

    public OrigemTipoAmostra getOrigem() {
        return origem;
    }

    public void setOrigem(OrigemTipoAmostra origem) {
        this.origem = origem;
    }

    public Integer getNumeroDeAmostras() {
        return numeroDeAmostras;
    }

    public void setNumeroDeAmostras(Integer numeroDeAmostras) {
        this.numeroDeAmostras = numeroDeAmostras;
    }

    public Integer getVolumeDaAmostra() {
        return volumeDaAmostra;
    }

    public void setVolumeDaAmostra(Integer volumeDaAmostra) {
        this.volumeDaAmostra = volumeDaAmostra;
    }

    public UnidadeDeMedida getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public Boolean isCongelado() {
        return congelado;
    }

    public void setCongelado(Boolean congelado) {
        this.congelado = congelado;
    }

    public Integer getUnidadeFuncionalId() {
        return unidadeFuncionalId;
    }

    public void setUnidadeFuncionalId(Integer unidadeFuncionalId) {
        this.unidadeFuncionalId = unidadeFuncionalId;
    }

    public Long getAmostraRecipienteId() {
        return amostraRecipienteId;
    }

    public void setAmostraRecipienteId(Long recipienteId) {
        this.amostraRecipienteId = recipienteId;
    }

    public String getAmostraRecipienteNome() {
        return amostraRecipienteNome;
    }

    public void setAmostraRecipienteNome(String recipienteNome) {
        this.amostraRecipienteNome = recipienteNome;
    }

    public Long getAmostraAnticoagulanteId() {
        return amostraAnticoagulanteId;
    }

    public void setAmostraAnticoagulanteId(Long anticoagulanteId) {
        this.amostraAnticoagulanteId = anticoagulanteId;
    }

    public String getAmostraAnticoagulanteNome() {
        return amostraAnticoagulanteNome;
    }

    public void setAmostraAnticoagulanteNome(String anticoagulanteNome) {
        this.amostraAnticoagulanteNome = anticoagulanteNome;
    }

    public Long getAmostraMaterialId() {
        return amostraMaterialId;
    }

    public void setAmostraMaterialId(Long materialId) {
        this.amostraMaterialId = materialId;
    }

    public String getAmostraMaterialNome() {
        return amostraMaterialNome;
    }

    public void setAmostraMaterialNome(String materialNome) {
        this.amostraMaterialNome = materialNome;
    }

    public Long getMaterialDeExameId() {
        return materialDeExameId;
    }

    public void setMaterialDeExameId(Long materialDeExameId) {
        this.materialDeExameId = materialDeExameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmostraDeMaterialDTO)) {
            return false;
        }

        return id != null && id.equals(((AmostraDeMaterialDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmostraDeMaterialDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", numeroDeAmostras=" + getNumeroDeAmostras() +
            ", volumeDaAmostra=" + getVolumeDaAmostra() +
            ", unidadeDeMedida='" + getUnidadeDeMedida() + "'" +
            ", responsavel='" + getResponsavel() + "'" +
            ", congelado='" + isCongelado() + "'" +
            ", unidadeFuncionalId=" + getUnidadeFuncionalId() +
            ", amostraRecipienteId=" + getAmostraRecipienteId() +
            ", amostraRecipienteNome='" + getAmostraRecipienteNome() + "'" +
            ", amostraAnticoagulanteId=" + getAmostraAnticoagulanteId() +
            ", amostraAnticoagulanteNome='" + getAmostraAnticoagulanteNome() + "'" +
            ", amostraMaterialId=" + getAmostraMaterialId() +
            ", amostraMaterialNome='" + getAmostraMaterialNome() + "'" +
            ", materialDeExameId=" + getMaterialDeExameId() +
            "}";
    }
}
