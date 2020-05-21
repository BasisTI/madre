package br.com.basis.madre.farmacia.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class PrescricaoDTO implements Serializable {

    private long id;
    private String unidade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescricaoDTO that = (PrescricaoDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PrescricaoDTO{" +
            "id=" + id +
            ", unidade='" + unidade + '\'' +
            '}';
    }
}
