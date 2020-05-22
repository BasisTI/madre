package br.com.basis.madre.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.CaraterDaInternacao} entity.
 */
public class CaraterDaInternacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CaraterDaInternacaoDTO caraterDaInternacaoDTO = (CaraterDaInternacaoDTO) o;
        if (caraterDaInternacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caraterDaInternacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaraterDaInternacaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
