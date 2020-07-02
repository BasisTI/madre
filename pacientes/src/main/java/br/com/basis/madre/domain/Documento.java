package br.com.basis.madre.domain;

import br.com.basis.madre.domain.validation.annotation.PIS;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Documento.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "documento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-documento")
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @Column(name = "numero_da_identidade")
    private String numeroDaIdentidade;

    @Field(type = FieldType.Date)
    @Column(name = "data")
    private LocalDate data;


    @Field(type = FieldType.Text)
    @CPF
    @Column(name = "cpf")
    private String cpf;

    @Field(type = FieldType.Text)
    @PIS
    @Column(name = "pis_pasep")
    private String pisPasep;

    @Field(type = FieldType.Text)
    @Size(min = 11, max = 11)
    @Column(name = "cnh")
    private String cnh;

    @Field(type = FieldType.Date)
    @Column(name = "validade_da_cnh")
    private LocalDate validadeDaCnh;

    @Field(type = FieldType.Boolean)
    @Column(name = "documentos_apresentados")
    private Boolean documentosApresentados;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("documentos")
    private OrgaoEmissor orgaoEmissor;

    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("documentos")
    private UF uf;

    public Documento numeroDaIdentidade(String numeroDaIdentidade) {
        this.numeroDaIdentidade = numeroDaIdentidade;
        return this;
    }

    public Documento data(LocalDate data) {
        this.data = data;
        return this;
    }

    public Documento cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public Documento pisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
        return this;
    }

    public Documento validadeDaCnh(LocalDate validadeDaCnh) {
        this.validadeDaCnh = validadeDaCnh;
        return this;
    }

    public Documento cnh(String cnh) {
        this.cnh = cnh;
        return this;
    }

    public Documento documentosApresentados(Boolean documentosApresentados) {
        this.documentosApresentados = documentosApresentados;
        return this;
    }

    public Boolean isDocumentosApresentados() {
        return documentosApresentados;
    }

    public Documento orgaoEmissor(OrgaoEmissor orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
        return this;
    }

    public Documento uf(UF uF) {
        this.uf = uF;
        return this;
    }


}
