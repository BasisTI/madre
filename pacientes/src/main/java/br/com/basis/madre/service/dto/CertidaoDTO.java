package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.TipoDaCertidao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Certidao} entity.
 */
public class CertidaoDTO implements Serializable {

    private Long id;

    private String registroDeNascimento;

    private TipoDaCertidao tipoDaCertidao;

    private String nomeDoCartorio;

    private String livro;

    private String folhas;

    private String termo;

    private LocalDate dataDeEmissao;

    private String numeroDaDeclaracaoDeNascimento;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistroDeNascimento() {
        return registroDeNascimento;
    }

    public void setRegistroDeNascimento(String registroDeNascimento) {
        this.registroDeNascimento = registroDeNascimento;
    }

    public TipoDaCertidao getTipoDaCertidao() {
        return tipoDaCertidao;
    }

    public void setTipoDaCertidao(TipoDaCertidao tipoDaCertidao) {
        this.tipoDaCertidao = tipoDaCertidao;
    }

    public String getNomeDoCartorio() {
        return nomeDoCartorio;
    }

    public void setNomeDoCartorio(String nomeDoCartorio) {
        this.nomeDoCartorio = nomeDoCartorio;
    }

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getFolhas() {
        return folhas;
    }

    public void setFolhas(String folhas) {
        this.folhas = folhas;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public LocalDate getDataDeEmissao() {
        return dataDeEmissao;
    }

    public void setDataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
    }

    public String getNumeroDaDeclaracaoDeNascimento() {
        return numeroDaDeclaracaoDeNascimento;
    }

    public void setNumeroDaDeclaracaoDeNascimento(String numeroDaDeclaracaoDeNascimento) {
        this.numeroDaDeclaracaoDeNascimento = numeroDaDeclaracaoDeNascimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CertidaoDTO certidaoDTO = (CertidaoDTO) o;
        if (certidaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), certidaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CertidaoDTO{" +
            "id=" + getId() +
            ", registroDeNascimento='" + getRegistroDeNascimento() + "'" +
            ", tipoDaCertidao='" + getTipoDaCertidao() + "'" +
            ", nomeDoCartorio='" + getNomeDoCartorio() + "'" +
            ", livro='" + getLivro() + "'" +
            ", folhas='" + getFolhas() + "'" +
            ", termo='" + getTermo() + "'" +
            ", dataDeEmissao='" + getDataDeEmissao() + "'" +
            ", numeroDaDeclaracaoDeNascimento='" + getNumeroDaDeclaracaoDeNascimento() + "'" +
            "}";
    }
}
