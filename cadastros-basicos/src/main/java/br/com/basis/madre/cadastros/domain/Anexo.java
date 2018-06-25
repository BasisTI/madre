package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Anexo.
 */
@Entity
@Table(name = "anexo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "anexo")
public class Anexo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Size(max = 20)
    @Column(name = "nome_arquivo", length = 20)
    private String nomeArquivo;

    @Column(name = "tamanho_arquivo")
    private Float tamanhoArquivo;

    @Lob
    @Column(name = "arquivo_anexo")
    private byte[] arquivoAnexo;

    @Column(name = "arquivo_anexo_content_type")
    private String arquivoAnexoContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public Anexo dataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public Anexo nomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        return this;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Float getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public Anexo tamanhoArquivo(Float tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
        return this;
    }

    public void setTamanhoArquivo(Float tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public byte[] getArquivoAnexo() {
        return arquivoAnexo.clone();
    }

    public Anexo arquivoAnexo(byte[] arquivoAnexo) {
        this.arquivoAnexo = arquivoAnexo.clone();
        return this;
    }

    public void setArquivoAnexo(byte[] arquivoAnexo) {
        this.arquivoAnexo = arquivoAnexo.clone();
    }

    public String getArquivoAnexoContentType() {
        return arquivoAnexoContentType;
    }

    public Anexo arquivoAnexoContentType(String arquivoAnexoContentType) {
        this.arquivoAnexoContentType = arquivoAnexoContentType;
        return this;
    }

    public void setArquivoAnexoContentType(String arquivoAnexoContentType) {
        this.arquivoAnexoContentType = arquivoAnexoContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Anexo anexo = (Anexo) o;
        if (anexo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Anexo{" +
            "id=" + getId() +
            ", dataCriacao='" + getDataCriacao() + "'" +
            ", nomeArquivo='" + getNomeArquivo() + "'" +
            ", tamanhoArquivo=" + getTamanhoArquivo() +
            ", arquivoAnexoContentType='" + getArquivoAnexoContentType() + "'" +
            "}";
    }
}
