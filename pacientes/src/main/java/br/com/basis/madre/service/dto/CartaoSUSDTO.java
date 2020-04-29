package br.com.basis.madre.service.dto;

import java.time.LocalDate;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.basis.madre.domain.enumeration.DocumentoDeReferencia;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the {@link br.com.basis.madre.domain.CartaoSUS} entity.
 */
public class CartaoSUSDTO implements Serializable {

    private Long id;


    @NotNull
    private String numero;

    @JsonProperty("docReferencia")
    @Enumerated(EnumType.STRING)
    private DocumentoDeReferencia documentoDeReferencia;

    @JsonProperty("cartaoNacional")
    private String cartaoNacionalSaudeMae;

    @JsonProperty("dataDeEntrada")
    private LocalDate dataDeEntradaNoBrasil;

    private LocalDate dataDeNaturalizacao;

    private String portaria;

    private JustificativaDTO justificativa;

    @JsonProperty("motivoCadastro")
    private MotivoDoCadastroDTO motivoDoCadastro;

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

    public JustificativaDTO getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(JustificativaDTO justificativa) {
        this.justificativa = justificativa;
    }

    public MotivoDoCadastroDTO getMotivoDoCadastro() {
        return motivoDoCadastro;
    }

    public void setMotivoDoCadastro(MotivoDoCadastroDTO motivoDoCadastro) {
        this.motivoDoCadastro = motivoDoCadastro;
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
            ", justificativa=" + justificativa +
            ", motivoDoCadastro=" + motivoDoCadastro +
            ", justificativaId=" + justificativaId +
            ", motivoDoCadastroId=" + motivoDoCadastroId +
            '}';
    }
}
