package br.com.basis.madre.seguranca.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.OrgaoEmissor} entity.
 */
public class OrgaoEmissorDTO implements Serializable {
    
    private Long id;

    private String valor;


    private Long documentosId;

    private String documentosNumeroDaIdentidade;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Long getDocumentosId() {
        return documentosId;
    }

    public void setDocumentosId(Long documentosId) {
        this.documentosId = documentosId;
    }

    public String getDocumentosNumeroDaIdentidade() {
        return documentosNumeroDaIdentidade;
    }

    public void setDocumentosNumeroDaIdentidade(String documentosNumeroDaIdentidade) {
        this.documentosNumeroDaIdentidade = documentosNumeroDaIdentidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrgaoEmissorDTO)) {
            return false;
        }

        return id != null && id.equals(((OrgaoEmissorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrgaoEmissorDTO{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            ", documentosId=" + getDocumentosId() +
            ", documentosNumeroDaIdentidade='" + getDocumentosNumeroDaIdentidade() + "'" +
            "}";
    }
}
