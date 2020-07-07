package br.com.basis.madre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A CEP.
 */
@Entity
@Table(name = "cep")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cep")
public class CEP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "municipio_id")
    private Long municipioId;

    @Column(name = "uf_id")
    private Long ufId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public CEP cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public CEP logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public CEP bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Long getMunicipioId() {
        return municipioId;
    }

    public CEP municipioId(Long municipioId) {
        this.municipioId = municipioId;
        return this;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    public Long getUfId() {
        return ufId;
    }

    public CEP ufId(Long ufId) {
        this.ufId = ufId;
        return this;
    }

    public void setUfId(Long ufId) {
        this.ufId = ufId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CEP)) {
            return false;
        }
        return id != null && id.equals(((CEP) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CEP{" +
            "id=" + getId() +
            ", cep='" + getCep() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", municipioId=" + getMunicipioId() +
            ", ufId=" + getUfId() +
            "}";
    }
}
