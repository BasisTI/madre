package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.Material} entity.
 */
public class MaterialDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 120)
    private String nome;

    @Size(max = 2000)
    private String descricao;

    @NotNull
    private Boolean ativo;

    @Size(max = 120)
    private String regimento;

    @Size(max = 500)
    private String observacao;

    @Min(value = 0L)
    private Long unidadeId;

    @Min(value = 0L)
    private Long procedimentoId;


    private Long unidadeMedidaId;

    private Long grupoId;

    private Long localEstoqueId;

    private Long codigoCatmatId;

    private Long sazonalidadeId;

    private Long tipoResiduoId;

    private Long origemParecerTecnicoId;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getRegimento() {
        return regimento;
    }

    public void setRegimento(String regimento) {
        this.regimento = regimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    public Long getProcedimentoId() {
        return procedimentoId;
    }

    public void setProcedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
    }

    public Long getUnidadeMedidaId() {
        return unidadeMedidaId;
    }

    public void setUnidadeMedidaId(Long unidadeMedidaId) {
        this.unidadeMedidaId = unidadeMedidaId;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoMaterialId) {
        this.grupoId = grupoMaterialId;
    }

    public Long getLocalEstoqueId() {
        return localEstoqueId;
    }

    public void setLocalEstoqueId(Long almoxarifadoId) {
        this.localEstoqueId = almoxarifadoId;
    }

    public Long getCodigoCatmatId() {
        return codigoCatmatId;
    }

    public void setCodigoCatmatId(Long codigoCatmatId) {
        this.codigoCatmatId = codigoCatmatId;
    }

    public Long getSazonalidadeId() {
        return sazonalidadeId;
    }

    public void setSazonalidadeId(Long sazonalidadeId) {
        this.sazonalidadeId = sazonalidadeId;
    }

    public Long getTipoResiduoId() {
        return tipoResiduoId;
    }

    public void setTipoResiduoId(Long tipoResiduoId) {
        this.tipoResiduoId = tipoResiduoId;
    }

    public Long getOrigemParecerTecnicoId() {
        return origemParecerTecnicoId;
    }

    public void setOrigemParecerTecnicoId(Long origemParecerTecnicoId) {
        this.origemParecerTecnicoId = origemParecerTecnicoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaterialDTO materialDTO = (MaterialDTO) o;
        if (materialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterialDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", regimento='" + getRegimento() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", unidadeId=" + getUnidadeId() +
            ", procedimentoId=" + getProcedimentoId() +
            ", unidadeMedida=" + getUnidadeMedidaId() +
            ", grupo=" + getGrupoId() +
            ", localEstoque=" + getLocalEstoqueId() +
            ", codigoCatmat=" + getCodigoCatmatId() +
            ", sazonalidade=" + getSazonalidadeId() +
            ", tipoResiduo=" + getTipoResiduoId() +
            ", origemParecerTecnico=" + getOrigemParecerTecnicoId() +
            "}";
    }
}
