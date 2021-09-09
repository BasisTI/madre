package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import br.com.basis.madre.madreexames.domain.enumeration.ConvenioPlano;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.ControleQualidade} entity.
 */
public class ControleQualidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer codigo;

    @NotNull
    private String material;

    @NotNull
    private String codigoConvenio;

    @NotNull
    private String codigoPlano;

    private ConvenioPlano convenioPlano;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCodigoConvenio() {
        return codigoConvenio;
    }

    public void setCodigoConvenio(String codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
    }

    public String getCodigoPlano() {
        return codigoPlano;
    }

    public void setCodigoPlano(String codigoPlano) {
        this.codigoPlano = codigoPlano;
    }

    public ConvenioPlano getConvenioPlano() {
        return convenioPlano;
    }

    public void setConvenioPlano(ConvenioPlano convenioPlano) {
        this.convenioPlano = convenioPlano;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControleQualidadeDTO)) {
            return false;
        }

        return id != null && id.equals(((ControleQualidadeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControleQualidadeDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", material='" + getMaterial() + "'" +
            ", codigoConvenio='" + getCodigoConvenio() + "'" +
            ", codigoPlano='" + getCodigoPlano() + "'" +
            ", convenioPlano='" + getConvenioPlano() + "'" +
            "}";
    }
}
