package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.DocumentoDeReferencia;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.CartaoSUS} entity.
 */
public class CartaoSUSDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    private DocumentoDeReferencia documentoDeReferencia;

    private String cartaoNacionalSaudeMae;

    private LocalDate dataDeEntradaNoBrasil;

    private LocalDate dataDeNaturalizacao;

    private String portaria;

    private Long justificativaId;

    private Long motivoDoCadastroId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public DocumentoDeReferencia getDocumentoDeReferencia() {
        return documentoDeReferencia;
    }

    public void setDocumentoDeReferencia(DocumentoDeReferencia documentoDeReferencia) {
        this.documentoDeReferencia = documentoDeReferencia;
    }

    public String getCartaoNacionalSaudeMae() {
        return cartaoNacionalSaudeMae;
    }

    public void setCartaoNacionalSaudeMae(String cartaoNacionalSaudeMae) {
        this.cartaoNacionalSaudeMae = cartaoNacionalSaudeMae;
    }

    public LocalDate getDataDeEntradaNoBrasil() {
        return dataDeEntradaNoBrasil;
    }

    public void setDataDeEntradaNoBrasil(LocalDate dataDeEntradaNoBrasil) {
        this.dataDeEntradaNoBrasil = dataDeEntradaNoBrasil;
    }

    public LocalDate getDataDeNaturalizacao() {
        return dataDeNaturalizacao;
    }

    public void setDataDeNaturalizacao(LocalDate dataDeNaturalizacao) {
        this.dataDeNaturalizacao = dataDeNaturalizacao;
    }

    public String getPortaria() {
        return portaria;
    }

    public void setPortaria(String portaria) {
        this.portaria = portaria;
    }

    public Long getJustificativaId() {
        return justificativaId;
    }

    public void setJustificativaId(Long justificativaId) {
        this.justificativaId = justificativaId;
    }

    public Long getMotivoDoCadastroId() {
        return motivoDoCadastroId;
    }

    public void setMotivoDoCadastroId(Long motivoDoCadastroId) {
        this.motivoDoCadastroId = motivoDoCadastroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartaoSUSDTO cartaoSUSDTO = (CartaoSUSDTO) o;
        if (cartaoSUSDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cartaoSUSDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CartaoSUSDTO{" +
            "id=" + id +
            ", numero='" + numero  +
            ", documentoDeReferencia=" + documentoDeReferencia +
            ", cartaoNacionalSaudeMae='" + cartaoNacionalSaudeMae  +
            ", dataDeEntradaNoBrasil=" + dataDeEntradaNoBrasil +
            ", dataDeNaturalizacao=" + dataDeNaturalizacao +
            ", portaria='" + portaria  +
            ", justificativaId=" + justificativaId +
            ", motivoDoCadastroId=" + motivoDoCadastroId +
            '}';
    }
}
