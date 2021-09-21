package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Pessoa} entity.
 */
public class PessoaCadastradaDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer codigo;

    @NotNull
    private String nome;

    @NotNull
    private LocalDate dataDeNascimento;

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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
        if (!(o instanceof PessoaCadastradaDTO)) {
            return false;
        }

        return id != null && id.equals(((PessoaCadastradaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaCadastradaDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", dataDeNascimento='" + getDataDeNascimento() + "'" +
            "}";
    }
}
