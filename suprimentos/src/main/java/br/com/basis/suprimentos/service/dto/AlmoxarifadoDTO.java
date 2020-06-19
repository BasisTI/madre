package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.Almoxarifado} entity.
 */
public class AlmoxarifadoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 120)
    private String descricao;

    @Min(value = 0)
    @Max(value = 255)
    private Integer diasEstoque;

    private Boolean central;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Boolean calculaMediaPonderada;

    private Boolean bloqueiaEntradaTransferencia;


    private Long centroDeAtividadeId;

    private Long caracteristicaArmazenamentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDiasEstoque() {
        return diasEstoque;
    }

    public void setDiasEstoque(Integer diasEstoque) {
        this.diasEstoque = diasEstoque;
    }

    public Boolean isCentral() {
        return central;
    }

    public void setCentral(Boolean central) {
        this.central = central;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isCalculaMediaPonderada() {
        return calculaMediaPonderada;
    }

    public void setCalculaMediaPonderada(Boolean calculaMediaPonderada) {
        this.calculaMediaPonderada = calculaMediaPonderada;
    }

    public Boolean isBloqueiaEntradaTransferencia() {
        return bloqueiaEntradaTransferencia;
    }

    public void setBloqueiaEntradaTransferencia(Boolean bloqueiaEntradaTransferencia) {
        this.bloqueiaEntradaTransferencia = bloqueiaEntradaTransferencia;
    }

    public Long getCentroDeAtividadeId() {
        return centroDeAtividadeId;
    }

    public void setCentroDeAtividadeId(Long centroDeAtividadeId) {
        this.centroDeAtividadeId = centroDeAtividadeId;
    }

    public Long getCaracteristicaArmazenamentoId() {
        return caracteristicaArmazenamentoId;
    }

    public void setCaracteristicaArmazenamentoId(Long caracteristicaArmazenamentoId) {
        this.caracteristicaArmazenamentoId = caracteristicaArmazenamentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmoxarifadoDTO almoxarifadoDTO = (AlmoxarifadoDTO) o;
        if (almoxarifadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), almoxarifadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlmoxarifadoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", diasEstoque=" + getDiasEstoque() +
            ", central='" + isCentral() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", calculaMediaPonderada='" + isCalculaMediaPonderada() + "'" +
            ", bloqueiaEntradaTransferencia='" + isBloqueiaEntradaTransferencia() + "'" +
            ", centroDeAtividade=" + getCentroDeAtividadeId() +
            ", caracteristicaArmazenamento=" + getCaracteristicaArmazenamentoId() +
            "}";
    }
}
