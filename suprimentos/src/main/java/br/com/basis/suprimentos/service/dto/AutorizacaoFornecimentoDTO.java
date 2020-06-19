package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.basis.suprimentos.domain.enumeration.TipoItemAf;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.AutorizacaoFornecimento} entity.
 */
public class AutorizacaoFornecimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long numero;

    @NotNull
    @Size(max = 120)
    private String complemento;

    @NotNull
    private TipoItemAf tipoItem;


    private Long fornecedorId;

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

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public TipoItemAf getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItemAf tipoItem) {
        this.tipoItem = tipoItem;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO = (AutorizacaoFornecimentoDTO) o;
        if (autorizacaoFornecimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autorizacaoFornecimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutorizacaoFornecimentoDTO{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", complemento='" + getComplemento() + "'" +
            ", tipoItem='" + getTipoItem() + "'" +
            ", fornecedor=" + getFornecedorId() +
            "}";
    }
}
