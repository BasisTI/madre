package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Documento} entity.
 */
public class DocumentoDTO implements Serializable {

    private Long id;

    private String numeroDaIdentidade;

    private LocalDate data;

    private String cpf;

    private String pisPasep;

    private String cnh;

    private LocalDate validadeDaCnh;

    private Boolean documentosApresentados;


    private Long orgaoEmissorId;

    private Long ufId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDaIdentidade() {
        return numeroDaIdentidade;
    }

    public void setNumeroDaIdentidade(String numeroDaIdentidade) {
        this.numeroDaIdentidade = numeroDaIdentidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public LocalDate getValidadeDaCnh() {
        return validadeDaCnh;
    }

    public void setValidadeDaCnh(LocalDate validadeDaCnh) {
        this.validadeDaCnh = validadeDaCnh;
    }

    public Boolean isDocumentosApresentados() {
        return documentosApresentados;
    }

    public void setDocumentosApresentados(Boolean documentosApresentados) {
        this.documentosApresentados = documentosApresentados;
    }

    public Long getOrgaoEmissorId() {
        return orgaoEmissorId;
    }

    public void setOrgaoEmissorId(Long orgaoEmissorId) {
        this.orgaoEmissorId = orgaoEmissorId;
    }

    public Long getUfId() {
        return ufId;
    }

    public void setUfId(Long uFId) {
        this.ufId = uFId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentoDTO documentoDTO = (DocumentoDTO) o;
        if (documentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentoDTO{" +
            "id=" + getId() +
            ", numeroDaIdentidade='" + getNumeroDaIdentidade() + "'" +
            ", data='" + getData() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", pisPasep='" + getPisPasep() + "'" +
            ", cnh='" + getCnh() + "'" +
            ", validadeDaCnh='" + getValidadeDaCnh() + "'" +
            ", documentosApresentados='" + isDocumentosApresentados() + "'" +
            ", orgaoEmissorId=" + getOrgaoEmissorId() +
            ", ufId=" + getUfId() +
            "}";
    }
}
