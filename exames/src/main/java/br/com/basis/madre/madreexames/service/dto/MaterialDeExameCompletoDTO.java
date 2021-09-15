package br.com.basis.madre.madreexames.service.dto;

import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
public class MaterialDeExameCompletoDTO extends MaterialDeExameDTO {

    private  Set<AmostraDeMaterialDTO> amostras;

    public Set<AmostraDeMaterialDTO> getAmostras() {
        Set<AmostraDeMaterialDTO> listaAmostra = amostras;
        return listaAmostra;
    }

    public void setAmostras(Set<AmostraDeMaterialDTO> amostras) {
        amostras = new HashSet<>(amostras);
        this.amostras = Collections.unmodifiableSet(amostras);
    }

}
