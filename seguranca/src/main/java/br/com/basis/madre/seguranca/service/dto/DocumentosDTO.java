package br.com.basis.madre.seguranca.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Documentos} entity.
 */
public class DocumentosDTO implements Serializable {
    
    private Long id;

    private Integer numeroDaIdentidade;

    private LocalDate dataDeEmissao;

    private String pisPasep;

    private String codigoPac;

    private String ctps;

    private String serie;

    private String tituloEleitoral;

    private String zona;

    private String secao;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroDaIdentidade() {
        return numeroDaIdentidade;
    }

    public void setNumeroDaIdentidade(Integer numeroDaIdentidade) {
        this.numeroDaIdentidade = numeroDaIdentidade;
    }

    public LocalDate getDataDeEmissao() {
        return dataDeEmissao;
    }

    public void setDataDeEmissao(LocalDate dataDeEmissao) {
        this.dataDeEmissao = dataDeEmissao;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getCodigoPac() {
        return codigoPac;
    }

    public void setCodigoPac(String codigoPac) {
        this.codigoPac = codigoPac;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getTituloEleitoral() {
        return tituloEleitoral;
    }

    public void setTituloEleitoral(String tituloEleitoral) {
        this.tituloEleitoral = tituloEleitoral;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSecao() {
        return secao;
    }

    public void setSecao(String secao) {
        this.secao = secao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentosDTO)) {
            return false;
        }

        return id != null && id.equals(((DocumentosDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentosDTO{" +
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
