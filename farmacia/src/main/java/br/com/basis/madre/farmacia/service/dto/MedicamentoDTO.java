package br.com.basis.madre.farmacia.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.farmacia.domain.Medicamento} entity.
 */
public class MedicamentoDTO implements Serializable {

    private Long id;

    private String codigo;

    private String nome;

    private String descricao;

    private String concentracao;

    private Boolean ativo;


    private Long tipoMedicamentoId;

    private Long apresentacaoId;

    private Long unidadeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getConcentracao() {
        return concentracao;
    }

    public void setConcentracao(String concentracao) {
        this.concentracao = concentracao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getTipoMedicamentoId() {
        return tipoMedicamentoId;
    }

    public void setTipoMedicamentoId(Long tipoMedicamentoId) {
        this.tipoMedicamentoId = tipoMedicamentoId;
    }

    public Long getApresentacaoId() {
        return apresentacaoId;
    }

    public void setApresentacaoId(Long apresentacaoId) {
        this.apresentacaoId = apresentacaoId;
    }

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicamentoDTO medicamentoDTO = (MedicamentoDTO) o;
        if (medicamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicamentoDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", concentracao='" + getConcentracao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", tipoMedicamento=" + getTipoMedicamentoId() +
            ", apresentacao=" + getApresentacaoId() +
            ", unidade=" + getUnidadeId() +
            "}";
    }
}
