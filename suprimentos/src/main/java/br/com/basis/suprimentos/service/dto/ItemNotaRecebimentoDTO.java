package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.ItemNotaRecebimento} entity.
 */
public class ItemNotaRecebimentoDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0L)
    @Max(value = 255L)
    private Long quantidadeReceber;

    @NotNull
    @Size(max = 255)
    private String quantidadeConvertida;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valorTotal;


    private Long recebimentoId;

    private Long marcaComercialId;

    private Long materialId;

    private Long unidadeMedidaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantidadeReceber() {
        return quantidadeReceber;
    }

    public void setQuantidadeReceber(Long quantidadeReceber) {
        this.quantidadeReceber = quantidadeReceber;
    }

    public String getQuantidadeConvertida() {
        return quantidadeConvertida;
    }

    public void setQuantidadeConvertida(String quantidadeConvertida) {
        this.quantidadeConvertida = quantidadeConvertida;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getRecebimentoId() {
        return recebimentoId;
    }

    public void setRecebimentoId(Long recebimentoId) {
        this.recebimentoId = recebimentoId;
    }

    public Long getMarcaComercialId() {
        return marcaComercialId;
    }

    public void setMarcaComercialId(Long marcaComercialId) {
        this.marcaComercialId = marcaComercialId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getUnidadeMedidaId() {
        return unidadeMedidaId;
    }

    public void setUnidadeMedidaId(Long unidadeMedidaId) {
        this.unidadeMedidaId = unidadeMedidaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO = (ItemNotaRecebimentoDTO) o;
        if (itemNotaRecebimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemNotaRecebimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemNotaRecebimentoDTO{" +
            "id=" + getId() +
            ", quantidadeReceber=" + getQuantidadeReceber() +
            ", quantidadeConvertida='" + getQuantidadeConvertida() + "'" +
            ", valorTotal=" + getValorTotal() +
            ", recebimento=" + getRecebimentoId() +
            ", marcaComercial=" + getMarcaComercialId() +
            ", material=" + getMaterialId() +
            ", unidadeMedida=" + getUnidadeMedidaId() +
            "}";
    }
}
