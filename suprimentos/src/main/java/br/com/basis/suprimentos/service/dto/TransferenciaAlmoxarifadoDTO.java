package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado} entity.
 */
public class TransferenciaAlmoxarifadoDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean ativo;


    private Long origemId;

    private Long destinoId;

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

    public Long getOrigemId() {
        return origemId;
    }

    public void setOrigemId(Long almoxarifadoId) {
        this.origemId = almoxarifadoId;
    }

    public Long getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Long almoxarifadoId) {
        this.destinoId = almoxarifadoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO = (TransferenciaAlmoxarifadoDTO) o;
        if (transferenciaAlmoxarifadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transferenciaAlmoxarifadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransferenciaAlmoxarifadoDTO{" +
            "id=" + getId() +
            ", ativo='" + isAtivo() + "'" +
            ", origem=" + getOrigemId() +
            ", destino=" + getDestinoId() +
            "}";
    }
}
