package br.com.basis.madre.cadastros.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Anexo entity.
 */
public class AnexoDTO implements Serializable {

    private Long id;

    private LocalDate dataCriacao;

    @Size(max = 20)
    private String nomeArquivo;

    private Float tamanhoArquivo;

    @Lob
    private byte[] arquivoAnexo;
    private String arquivoAnexoContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Float getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(Float tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public byte[] getArquivoAnexo() {
        return arquivoAnexo;
    }

    public void setArquivoAnexo(byte[] arquivoAnexo) {
        this.arquivoAnexo = arquivoAnexo;
    }

    public String getArquivoAnexoContentType() {
        return arquivoAnexoContentType;
    }

    public void setArquivoAnexoContentType(String arquivoAnexoContentType) {
        this.arquivoAnexoContentType = arquivoAnexoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnexoDTO anexoDTO = (AnexoDTO) o;
        if(anexoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnexoDTO{" +
            "id=" + getId() +
            ", dataCriacao='" + getDataCriacao() + "'" +
            ", nomeArquivo='" + getNomeArquivo() + "'" +
            ", tamanhoArquivo=" + getTamanhoArquivo() +
            ", arquivoAnexo='" + getArquivoAnexo() + "'" +
            "}";
    }
}
