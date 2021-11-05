package br.com.basis.madre.seguranca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Documentos.
 */
@Entity
@Table(name = "documentos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-documentos")
public class Documentos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqDocumentos")
    @SequenceGenerator(name = "seqDocumentos")
    private Long id;

    @Column(name = "numero_da_identidade")
    private Integer numeroDaIdentidade;

    @Column(name = "data_de_emissao")
    private LocalDate dataDeEmissao;

    @Column(name = "pis_pasep")
    private String pisPasep;

    @Column(name = "codigo_pac")
    private String codigoPac;

    @Column(name = "ctps")
    private String ctps;

    @Column(name = "serie")
    private String serie;

    @Column(name = "titulo_eleitoral")
    private String tituloEleitoral;

    @Column(name = "zona")
    private String zona;

    @Column(name = "secao")
    private String secao;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroDaIdentidade() {
        return numeroDaIdentidade;
    }

    public Documentos numeroDaIdentidade(Integer numeroDaIdentidade) {
        this.numeroDaIdentidade = numeroDaIdentidade;
        return this;
    }

    public void setNumeroDaIdentidade(Integer numeroDaIdentidade) {
        this.numeroDaIdentidade = numeroDaIdentidade;
    }

    public LocalDate getDataDeEmissao() {
        return dataDeEmissao;
    }

    public Documentos dataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
        return this;
    }

    public void setDataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public Documentos pisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
        return this;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getCodigoPac() {
        return codigoPac;
    }

    public Documentos codigoPac(String codigoPac) {
        this.codigoPac = codigoPac;
        return this;
    }

    public void setCodigoPac(String codigoPac) {
        this.codigoPac = codigoPac;
    }

    public String getCtps() {
        return ctps;
    }

    public Documentos ctps(String ctps) {
        this.ctps = ctps;
        return this;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getSerie() {
        return serie;
    }

    public Documentos serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getTituloEleitoral() {
        return tituloEleitoral;
    }

    public Documentos tituloEleitoral(String tituloEleitoral) {
        this.tituloEleitoral = tituloEleitoral;
        return this;
    }

    public void setTituloEleitoral(String tituloEleitoral) {
        this.tituloEleitoral = tituloEleitoral;
    }

    public String getZona() {
        return zona;
    }

    public Documentos zona(String zona) {
        this.zona = zona;
        return this;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSecao() {
        return secao;
    }

    public Documentos secao(String secao) {
        this.secao = secao;
        return this;
    }

    public void setSecao(String secao) {
        this.secao = secao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documentos)) {
            return false;
        }
        return id != null && id.equals(((Documentos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documentos{" +
            "id=" + getId() +
            ", numeroDaIdentidade=" + getNumeroDaIdentidade() +
            ", dataDeEmissao='" + getDataDeEmissao() + "'" +
            ", pisPasep='" + getPisPasep() + "'" +
            ", codigoPac='" + getCodigoPac() + "'" +
            ", ctps='" + getCtps() + "'" +
            ", serie='" + getSerie() + "'" +
            ", tituloEleitoral='" + getTituloEleitoral() + "'" +
            ", zona='" + getZona() + "'" +
            ", secao='" + getSecao() + "'" +
            "}";
    }
}
