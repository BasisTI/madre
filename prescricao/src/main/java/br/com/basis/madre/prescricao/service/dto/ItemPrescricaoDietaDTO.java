package br.com.basis.madre.prescricao.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoDieta} entity.
 */
public class ItemPrescricaoDietaDTO implements Serializable {

    private Long id;

    @DecimalMin(value = "0")
    private BigDecimal quantidade;

    @Min(value = 0)
    private Integer frequencia;

    private Integer numeroVezes;


    private Long tipoItemDietaId;

    private Long tipoAprazamentoId;

    private Long tipoUnidadeDietaId;

    private Long prescricaoDietaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public Integer getNumeroVezes() {
        return numeroVezes;
    }

    public void setNumeroVezes(Integer numeroVezes) {
        this.numeroVezes = numeroVezes;
    }

    public Long getTipoItemDietaId() {
        return tipoItemDietaId;
    }

    public void setTipoItemDietaId(Long tipoItemDietaId) {
        this.tipoItemDietaId = tipoItemDietaId;
    }

    public Long getTipoAprazamentoId() {
        return tipoAprazamentoId;
    }

    public void setTipoAprazamentoId(Long tipoAprazamentoId) {
        this.tipoAprazamentoId = tipoAprazamentoId;
    }

    public Long getTipoUnidadeDietaId() {
        return tipoUnidadeDietaId;
    }

    public void setTipoUnidadeDietaId(Long tipoUnidadeDietaId) {
        this.tipoUnidadeDietaId = tipoUnidadeDietaId;
    }

    public Long getPrescricaoDietaId() {
        return prescricaoDietaId;
    }

    public void setPrescricaoDietaId(Long prescricaoDietaId) {
        this.prescricaoDietaId = prescricaoDietaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemPrescricaoDietaDTO itemPrescricaoDietaDTO = (ItemPrescricaoDietaDTO) o;
        if (itemPrescricaoDietaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemPrescricaoDietaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemPrescricaoDietaDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", frequencia=" + getFrequencia() +
            ", numeroVezes=" + getNumeroVezes() +
            ", tipoItemDieta=" + getTipoItemDietaId() +
            ", tipoAprazamento=" + getTipoAprazamentoId() +
            ", tipoUnidadeDieta=" + getTipoUnidadeDietaId() +
            ", prescricaoDieta=" + getPrescricaoDietaId() +
            "}";
    }
}
