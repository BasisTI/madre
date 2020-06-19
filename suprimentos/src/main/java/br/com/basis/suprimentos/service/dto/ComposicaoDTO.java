package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.Composicao} entity.
 */
public class ComposicaoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 120)
    private String nome;

    @NotNull
    private String servidor;


    private Long caracteristicaArmazenamentoId;

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

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
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

        ComposicaoDTO composicaoDTO = (ComposicaoDTO) o;
        if (composicaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), composicaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComposicaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", servidor='" + getServidor() + "'" +
            ", caracteristicaArmazenamento=" + getCaracteristicaArmazenamentoId() +
            "}";
    }
}
