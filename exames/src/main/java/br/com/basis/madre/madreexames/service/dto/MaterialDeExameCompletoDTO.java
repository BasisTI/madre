package br.com.basis.madre.madreexames.service.dto;

import java.util.Set;

public class MaterialDeExameCompletoDTO extends MaterialDeExameDTO {

    private  Set<AmostraDeMaterialDTO> amostras;

    public Set<AmostraDeMaterialDTO> getAmostras() {
        return amostras;
    }

    public void setAmostras(Set<AmostraDeMaterialDTO> amostras) {
        this.amostras = amostras;
    }
}
