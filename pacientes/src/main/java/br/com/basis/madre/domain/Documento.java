package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Max;


import org.springframework.data.elasticsearch.annotations.Field;

import org.hibernate.validator.constraints.br.CPF;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Documento.
 */
@Entity
@Table(name = "documento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "documento")
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
    @Column(name = "pis_pasep")
    private String pisPasep;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDaIdentidade() {
        return numeroDaIdentidade;
    }

    public Documento numeroDaIdentidade(String numeroDaIdentidade) {
        this.numeroDaIdentidade = numeroDaIdentidade;
        return this;
    }

    public void setNumeroDaIdentidade(String numeroDaIdentidade) {
        this.numeroDaIdentidade = numeroDaIdentidade;
    }

    public LocalDate getData() {
        return data;
    }

    public Documento data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCpf() {
        return cpf;
    }

    public Documento cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }



    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public Documento pisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
        return this;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public LocalDate getValidadeDaCnh() {
        return validadeDaCnh;
    }

    public Documento validadeDaCnh(LocalDate validadeDaCnh) {
        this.validadeDaCnh = validadeDaCnh;
        return this;
    }

    public void setValidadeDaCnh(LocalDate validadeDaCnh) {
        this.validadeDaCnh = validadeDaCnh;
    }

    public Boolean isDocumentosApresentados() {
        return documentosApresentados;
    }

    public Documento documentosApresentados(Boolean documentosApresentados) {
        this.documentosApresentados = documentosApresentados;
        return this;
    }

    public void setDocumentosApresentados(Boolean documentosApresentados) {
        this.documentosApresentados = documentosApresentados;
    }

    public OrgaoEmissor getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public Documento orgaoEmissor(OrgaoEmissor orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
        return this;
    }

    public void setOrgaoEmissor(OrgaoEmissor orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public UF getUf() {
        return uf;
    }

    public Documento uf(UF uF) {
        this.uf = uF;
        return this;
    }

    public void setUf(UF uF) {
        this.uf = uF;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documento)) {
            return false;
        }
        return id != null && id.equals(((Documento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Documento{" +
            "id=" + getId() +
            ", numeroDaIdentidade='" + getNumeroDaIdentidade() + "'" +
            ", data='" + getData() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", pisPasep='" + getPisPasep() + "'" +
            ", validadeDaCnh='" + getValidadeDaCnh() + "'" +
            ", documentosApresentados='" + isDocumentosApresentados() + "'" +
            "}";
    }
}
