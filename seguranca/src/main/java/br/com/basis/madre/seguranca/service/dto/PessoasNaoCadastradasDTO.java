package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Pessoa} entity.
 */
public class PessoasNaoCadastradasDTO extends DominioComNome implements Serializable {

    @NotNull
    private LocalDate dataDeNascimento;


    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoasNaoCadastradasDTO)) {
            return false;
        }

        return id != null && id.equals(((PessoasNaoCadastradasDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", dataDeNascimento='" + getDataDeNascimento() + "'" +

            "}";
    }
}
