package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.SolicitacaoCompras} entity.
 */
public class SolicitacaoComprasDTO implements Serializable {

    private Long id;

    @NotNull
    private Long numero;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SolicitacaoComprasDTO solicitacaoComprasDTO = (SolicitacaoComprasDTO) o;
        if (solicitacaoComprasDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitacaoComprasDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolicitacaoComprasDTO{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            "}";
    }
}
