package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.TipoDaCertidao;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Certidao.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "certidao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-certidao")
public class Certidao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @Column(name = "registro_de_nascimento")
    private String registroDeNascimento;

    @Field(type = FieldType.Keyword)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_da_certidao")
    private TipoDaCertidao tipoDaCertidao;

    @Field(type = FieldType.Text)
    @Column(name = "nome_do_cartorio")
    private String nomeDoCartorio;

    @Field(type = FieldType.Text)
    @Column(name = "livro")
    private String livro;

    @Field(type = FieldType.Text)
    @Column(name = "folhas")
    private String folhas;

    @Field(type = FieldType.Text)
    @Column(name = "termo")
    private String termo;

    @Field(type = FieldType.Date)
    @Column(name = "data_de_emissao")
    private LocalDate dataDeEmissao;

    @Field(type = FieldType.Text)
    @Column(name = "numero_da_declaracao_de_nascimento")
    private String numeroDaDeclaracaoDeNascimento;


    public Certidao registroDeNascimento(String registroDeNascimento) {
        this.registroDeNascimento = registroDeNascimento;
        return this;
    }

    public Certidao tipoDaCertidao(TipoDaCertidao tipoDaCertidao) {
        this.tipoDaCertidao = tipoDaCertidao;
        return this;
    }

    public Certidao nomeDoCartorio(String nomeDoCartorio) {
        this.nomeDoCartorio = nomeDoCartorio;
        return this;
    }

    public Certidao livro(String livro) {
        this.livro = livro;
        return this;
    }

    public Certidao folhas(String folhas) {
        this.folhas = folhas;
        return this;
    }

    public Certidao termo(String termo) {
        this.termo = termo;
        return this;
    }

    public Certidao dataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
        return this;
    }

    public Certidao numeroDaDeclaracaoDeNascimento(String numeroDaDeclaracaoDeNascimento) {
        this.numeroDaDeclaracaoDeNascimento = numeroDaDeclaracaoDeNascimento;
        return this;
    }


}
