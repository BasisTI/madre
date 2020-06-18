package br.com.basis.suprimentos.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.Lote} entity.
 */
public class LoteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 120)
    private String descricao;

    @Size(min = 1, max = 15)
    private String serie;

    @Min(value = 0L)
    private Long quantidadeDisponivel;

    @Min(value = 0L)
    private Long quantidadeBloqueada;

    @Min(value = 0L)
    private Long quantidadeProblema;

    @NotNull
    private LocalDate dataValidade;


    private Long marcaComercialId;

    private Long estoqueId;

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

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Long getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Long quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Long getQuantidadeBloqueada() {
        return quantidadeBloqueada;
    }

    public void setQuantidadeBloqueada(Long quantidadeBloqueada) {
        this.quantidadeBloqueada = quantidadeBloqueada;
    }

    public Long getQuantidadeProblema() {
        return quantidadeProblema;
    }

    public void setQuantidadeProblema(Long quantidadeProblema) {
        this.quantidadeProblema = quantidadeProblema;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Long getMarcaComercialId() {
        return marcaComercialId;
    }

    public void setMarcaComercialId(Long marcaComercialId) {
        this.marcaComercialId = marcaComercialId;
    }

    public Long getEstoqueId() {
        return estoqueId;
    }

    public void setEstoqueId(Long estoqueAlmoxarifadoId) {
        this.estoqueId = estoqueAlmoxarifadoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoteDTO loteDTO = (LoteDTO) o;
        if (loteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoteDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", serie='" + getSerie() + "'" +
            ", quantidadeDisponivel=" + getQuantidadeDisponivel() +
            ", quantidadeBloqueada=" + getQuantidadeBloqueada() +
            ", quantidadeProblema=" + getQuantidadeProblema() +
            ", dataValidade='" + getDataValidade() + "'" +
            ", marcaComercial=" + getMarcaComercialId() +
            ", estoque=" + getEstoqueId() +
            "}";
    }
}
