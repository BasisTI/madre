package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.EstoqueAlmoxarifado} entity.
 */
public class EstoqueAlmoxarifadoDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean ativo;

    @Size(min = 1, max = 8)
    private String endereco;

    private Long quantidadeLimiteArmazenamento;

    @NotNull
    @Min(value = 1L)
    private Long quantidadeEstoqueMinimo;

    @NotNull
    @Min(value = 1L)
    private Long quantidadeEstoqueMaximo;

    @NotNull
    @Min(value = 1L)
    private Long quantidadePontoPedido;

    @NotNull
    @Min(value = 1)
    private Integer tempoReposicao;


    private Long almoxarifadoId;

    private Long materialId;

    private Long fornecedorId;

    private Long solicitacaoComprasId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getQuantidadeLimiteArmazenamento() {
        return quantidadeLimiteArmazenamento;
    }

    public void setQuantidadeLimiteArmazenamento(Long quantidadeLimiteArmazenamento) {
        this.quantidadeLimiteArmazenamento = quantidadeLimiteArmazenamento;
    }

    public Long getQuantidadeEstoqueMinimo() {
        return quantidadeEstoqueMinimo;
    }

    public void setQuantidadeEstoqueMinimo(Long quantidadeEstoqueMinimo) {
        this.quantidadeEstoqueMinimo = quantidadeEstoqueMinimo;
    }

    public Long getQuantidadeEstoqueMaximo() {
        return quantidadeEstoqueMaximo;
    }

    public void setQuantidadeEstoqueMaximo(Long quantidadeEstoqueMaximo) {
        this.quantidadeEstoqueMaximo = quantidadeEstoqueMaximo;
    }

    public Long getQuantidadePontoPedido() {
        return quantidadePontoPedido;
    }

    public void setQuantidadePontoPedido(Long quantidadePontoPedido) {
        this.quantidadePontoPedido = quantidadePontoPedido;
    }

    public Integer getTempoReposicao() {
        return tempoReposicao;
    }

    public void setTempoReposicao(Integer tempoReposicao) {
        this.tempoReposicao = tempoReposicao;
    }

    public Long getAlmoxarifadoId() {
        return almoxarifadoId;
    }

    public void setAlmoxarifadoId(Long almoxarifadoId) {
        this.almoxarifadoId = almoxarifadoId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public Long getSolicitacaoComprasId() {
        return solicitacaoComprasId;
    }

    public void setSolicitacaoComprasId(Long solicitacaoComprasId) {
        this.solicitacaoComprasId = solicitacaoComprasId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = (EstoqueAlmoxarifadoDTO) o;
        if (estoqueAlmoxarifadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estoqueAlmoxarifadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstoqueAlmoxarifadoDTO{" +
            "id=" + getId() +
            ", ativo='" + isAtivo() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", quantidadeLimiteArmazenamento=" + getQuantidadeLimiteArmazenamento() +
            ", quantidadeEstoqueMinimo=" + getQuantidadeEstoqueMinimo() +
            ", quantidadeEstoqueMaximo=" + getQuantidadeEstoqueMaximo() +
            ", quantidadePontoPedido=" + getQuantidadePontoPedido() +
            ", tempoReposicao=" + getTempoReposicao() +
            ", almoxarifado=" + getAlmoxarifadoId() +
            ", material=" + getMaterialId() +
            ", fornecedor=" + getFornecedorId() +
            ", solicitacaoCompras=" + getSolicitacaoComprasId() +
            "}";
    }
}
