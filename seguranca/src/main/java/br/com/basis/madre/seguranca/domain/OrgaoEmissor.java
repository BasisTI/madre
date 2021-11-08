package br.com.basis.madre.seguranca.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrgaoEmissor.
 */
@Entity
@Table(name = "orgao_emissor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-orgaoemissor")
public class OrgaoEmissor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqOrgaoEmissor")
    @SequenceGenerator(name = "seqOrgaoEmissor")
    private Long id;

    @Column(name = "valor")
    private String valor;

    @ManyToOne
    @JsonIgnoreProperties(value = "orgaoEmissors", allowSetters = true)
    private Documentos documentos;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public OrgaoEmissor valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Documentos getDocumentos() {
        return documentos;
    }

    public OrgaoEmissor documentos(Documentos documentos) {
        this.documentos = documentos;
        return this;
    }

    public void setDocumentos(Documentos documentos) {
        this.documentos = documentos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrgaoEmissor)) {
            return false;
        }
        return id != null && id.equals(((OrgaoEmissor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrgaoEmissor{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
