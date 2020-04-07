package br.com.basis.madre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

import br.com.basis.madre.domain.enumeration.TipoDaCertidao;

/**
 * A Certidao.
 */
@Entity
@Table(name = "certidao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "certidao")
public class Certidao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "registro_de_nascimento")
    private String registroDeNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_da_certidao")
    private TipoDaCertidao tipoDaCertidao;

    @Column(name = "nome_do_cartorio")
    private String nomeDoCartorio;

    @Column(name = "livro")
    private String livro;

    @Column(name = "folhas")
    private String folhas;

    @Column(name = "termo")
    private String termo;

    @Column(name = "data_de_emissao")
    private LocalDate dataDeEmissao;

    @Column(name = "numero_da_declaracao_de_nascimento")
    private String numeroDaDeclaracaoDeNascimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistroDeNascimento() {
        return registroDeNascimento;
    }

    public Certidao registroDeNascimento(String registroDeNascimento) {
        this.registroDeNascimento = registroDeNascimento;
        return this;
    }

    public void setRegistroDeNascimento(String registroDeNascimento) {
        this.registroDeNascimento = registroDeNascimento;
    }

    public TipoDaCertidao getTipoDaCertidao() {
        return tipoDaCertidao;
    }

    public Certidao tipoDaCertidao(TipoDaCertidao tipoDaCertidao) {
        this.tipoDaCertidao = tipoDaCertidao;
        return this;
    }

    public void setTipoDaCertidao(TipoDaCertidao tipoDaCertidao) {
        this.tipoDaCertidao = tipoDaCertidao;
    }

    public String getNomeDoCartorio() {
        return nomeDoCartorio;
    }

    public Certidao nomeDoCartorio(String nomeDoCartorio) {
        this.nomeDoCartorio = nomeDoCartorio;
        return this;
    }

    public void setNomeDoCartorio(String nomeDoCartorio) {
        this.nomeDoCartorio = nomeDoCartorio;
    }

    public String getLivro() {
        return livro;
    }

    public Certidao livro(String livro) {
        this.livro = livro;
        return this;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getFolhas() {
        return folhas;
    }

    public Certidao folhas(String folhas) {
        this.folhas = folhas;
        return this;
    }

    public void setFolhas(String folhas) {
        this.folhas = folhas;
    }

    public String getTermo() {
        return termo;
    }

    public Certidao termo(String termo) {
        this.termo = termo;
        return this;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public LocalDate getDataDeEmissao() {
        return dataDeEmissao;
    }

    public Certidao dataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
        return this;
    }

    public void setDataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
    }

    public String getNumeroDaDeclaracaoDeNascimento() {
        return numeroDaDeclaracaoDeNascimento;
    }

    public Certidao numeroDaDeclaracaoDeNascimento(String numeroDaDeclaracaoDeNascimento) {
        this.numeroDaDeclaracaoDeNascimento = numeroDaDeclaracaoDeNascimento;
        return this;
    }

    public void setNumeroDaDeclaracaoDeNascimento(String numeroDaDeclaracaoDeNascimento) {
        this.numeroDaDeclaracaoDeNascimento = numeroDaDeclaracaoDeNascimento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Certidao)) {
            return false;
        }
        return id != null && id.equals(((Certidao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Certidao{" +
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
