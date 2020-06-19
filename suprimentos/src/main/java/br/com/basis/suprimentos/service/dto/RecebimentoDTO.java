package br.com.basis.suprimentos.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.Recebimento} entity.
 */
public class RecebimentoDTO implements Serializable {

    private Long id;


    private Long notaFiscalEntradaId;

    private Long autorizacaoFornecimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotaFiscalEntradaId() {
        return notaFiscalEntradaId;
    }

    public void setNotaFiscalEntradaId(Long documentoFiscalEntradaId) {
        this.notaFiscalEntradaId = documentoFiscalEntradaId;
    }

    public Long getAutorizacaoFornecimentoId() {
        return autorizacaoFornecimentoId;
    }

    public void setAutorizacaoFornecimentoId(Long autorizacaoFornecimentoId) {
        this.autorizacaoFornecimentoId = autorizacaoFornecimentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecebimentoDTO recebimentoDTO = (RecebimentoDTO) o;
        if (recebimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recebimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecebimentoDTO{" +
            "id=" + getId() +
            ", notaFiscalEntrada=" + getNotaFiscalEntradaId() +
            ", autorizacaoFornecimento=" + getAutorizacaoFornecimentoId() +
            "}";
    }
}
