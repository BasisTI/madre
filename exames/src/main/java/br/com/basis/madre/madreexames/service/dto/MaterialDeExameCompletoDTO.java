package br.com.basis.madre.madreexames.service.dto;

import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MaterialDeExameCompletoDTO that = (MaterialDeExameCompletoDTO) o;
        return Objects.equals(amostras, that.amostras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amostras);
    }
}
