package br.com.basis.madre.seguranca.service.dto;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.ConselhosProfissionais} entity.
 */
public class ConselhosProfissionaisDTO implements Serializable {

    private Long id;

    private Integer codigo;

    @NotNull
    private String nome;

    @NotNull
    private String sigla;

    private String tituloMasculino;

    private String tituloFeminino;

    private Boolean situacao;


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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTituloMasculino() {
        return tituloMasculino;
    }

    public void setTituloMasculino(String tituloMasculino) {
        this.tituloMasculino = tituloMasculino;
    }

    public String getTituloFeminino() {
        return tituloFeminino;
    }

    public void setTituloFeminino(String tituloFeminino) {
        this.tituloFeminino = tituloFeminino;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConselhosProfissionaisDTO)) {
            return false;
        }

        return id != null && id.equals(((ConselhosProfissionaisDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConselhosProfissionaisDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", tituloMasculino='" + getTituloMasculino() + "'" +
            ", tituloFeminino='" + getTituloFeminino() + "'" +
            ", situacao='" + isSituacao() + "'" +
            "}";
    }
}
