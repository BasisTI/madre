package br.com.basis.madre.madreexames.domain;

import br.com.basis.madre.madreexames.domain.enumeration.ConvenioPlano;
import br.com.basis.madre.madreexames.domain.enumeration.FormaEnvio;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A LaboratorioExterno.
 */
@Entity
@Table(name = "laboratorio_externo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-laboratorioexterno")
public class LaboratorioExterno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLaboratorioExterno")
    @SequenceGenerator(name = "seqLaboratorioExterno")
    private Long id;

    @NotNull
    @Column(name = "sigla", nullable = false)
    private String sigla;

    @NotNull
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @NotNull
    @Column(name = "municipio", nullable = false)
    private String municipio;

    @NotNull
    @Column(name = "cep", nullable = false)
    private Integer cep;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private Integer telefone;

    @NotNull
    @Column(name = "fax", nullable = false)
    private Integer fax;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "cgc", nullable = false)
    private String cgc;

    @NotNull
    @Column(name = "codigo_convenio", nullable = false)
    private String codigoConvenio;

    @NotNull
    @Column(name = "codigo_plano", nullable = false)
    private String codigoPlano;

    @Enumerated(EnumType.STRING)
    @Column(name = "convenio_plano")
    private ConvenioPlano convenioPlano;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_envio")
    private FormaEnvio formaEnvio;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LaboratorioExterno codigo(Integer codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public LaboratorioExterno nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public String getSigla() {
        return sigla;
    }

    public LaboratorioExterno sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEndereco() {
        return endereco;
    }

    public LaboratorioExterno endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMunicipio() {
        return municipio;
    }

    public LaboratorioExterno municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getCep() {
        return cep;
    }

    public LaboratorioExterno cep(Integer cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public LaboratorioExterno telefone(Integer telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Integer getFax() {
        return fax;
    }

    public LaboratorioExterno fax(Integer fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(Integer fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public LaboratorioExterno email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCgc() {
        return cgc;
    }

    public LaboratorioExterno cgc(String cgc) {
        this.cgc = cgc;
        return this;
    }

    public void setCgc(String cgc) {
        this.cgc = cgc;
    }

    public String getCodigoConvenio() {
        return codigoConvenio;
    }

    public LaboratorioExterno codigoConvenio(String codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
        return this;
    }

    public void setCodigoConvenio(String codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
    }

    public String getCodigoPlano() {
        return codigoPlano;
    }

    public LaboratorioExterno codigoPlano(String codigoPlano) {
        this.codigoPlano = codigoPlano;
        return this;
    }

    public void setCodigoPlano(String codigoPlano) {
        this.codigoPlano = codigoPlano;
    }

    public ConvenioPlano getConvenioPlano() {
        return convenioPlano;
    }

    public LaboratorioExterno convenioPlano(ConvenioPlano convenioPlano) {
        this.convenioPlano = convenioPlano;
        return this;
    }

    public void setConvenioPlano(ConvenioPlano convenioPlano) {
        this.convenioPlano = convenioPlano;
    }

    public FormaEnvio getFormaEnvio() {
        return formaEnvio;
    }

    public LaboratorioExterno formaEnvio(FormaEnvio formaEnvio) {
        this.formaEnvio = formaEnvio;
        return this;
    }

    public void setFormaEnvio(FormaEnvio formaEnvio) {
        this.formaEnvio = formaEnvio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LaboratorioExterno)) {
            return false;
        }
        return id != null && id.equals(((LaboratorioExterno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LaboratorioExterno{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", cep=" + getCep() +
            ", telefone=" + getTelefone() +
            ", fax=" + getFax() +
            ", email='" + getEmail() + "'" +
            ", cgc='" + getCgc() + "'" +
            ", codigoConvenio='" + getCodigoConvenio() + "'" +
            ", codigoPlano='" + getCodigoPlano() + "'" +
            ", convenioPlano='" + getConvenioPlano() + "'" +
            ", formaEnvio='" + getFormaEnvio() + "'" +
            "}";
    }
}
