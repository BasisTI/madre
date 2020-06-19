package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.basis.suprimentos.domain.enumeration.TipoTempoPorClasse;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.TempoPorClasse} entity.
 */
public class TempoPorClasseDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoTempoPorClasse tipo;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    private Integer quantidadeClasseA;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    private Integer quantidadeClasseB;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    private Integer quantidadeClasseC;


    private Long almoxarifadoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTempoPorClasse getTipo() {
        return tipo;
    }

    public void setTipo(TipoTempoPorClasse tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidadeClasseA() {
        return quantidadeClasseA;
    }

    public void setQuantidadeClasseA(Integer quantidadeClasseA) {
        this.quantidadeClasseA = quantidadeClasseA;
    }

    public Integer getQuantidadeClasseB() {
        return quantidadeClasseB;
    }

    public void setQuantidadeClasseB(Integer quantidadeClasseB) {
        this.quantidadeClasseB = quantidadeClasseB;
    }

    public Integer getQuantidadeClasseC() {
        return quantidadeClasseC;
    }

    public void setQuantidadeClasseC(Integer quantidadeClasseC) {
        this.quantidadeClasseC = quantidadeClasseC;
    }

    public Long getAlmoxarifadoId() {
        return almoxarifadoId;
    }

    public void setAlmoxarifadoId(Long almoxarifadoId) {
        this.almoxarifadoId = almoxarifadoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TempoPorClasseDTO tempoPorClasseDTO = (TempoPorClasseDTO) o;
        if (tempoPorClasseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tempoPorClasseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TempoPorClasseDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", quantidadeClasseA=" + getQuantidadeClasseA() +
            ", quantidadeClasseB=" + getQuantidadeClasseB() +
            ", quantidadeClasseC=" + getQuantidadeClasseC() +
            ", almoxarifado=" + getAlmoxarifadoId() +
            "}";
    }
}
