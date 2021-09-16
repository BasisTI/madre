package br.com.basis.madre.madreexames.service.dto;
import br.com.basis.madre.madreexames.domain.enumeration.ConvenioPlano;

import javax.validation.constraints.NotNull;

public class DominioConvenioPlano extends DominioIdNome {

    @NotNull
    private String codigoConvenio;

    @NotNull
    private String codigoPlano;

    @NotNull
    private Integer codigo;

    private ConvenioPlano convenioPlano;

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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;

    }
}
