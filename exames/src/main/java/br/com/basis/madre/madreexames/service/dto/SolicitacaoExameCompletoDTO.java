package br.com.basis.madre.madreexames.service.dto;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class SolicitacaoExameCompletoDTO extends SolicitacaoExameDTO {

    private Set<ItemSolicitacaoExameDTO> solicitacaoExames;

    public Set<ItemSolicitacaoExameDTO> getSolicitacaoExames() {
        Set<ItemSolicitacaoExameDTO> solicitacaoExamesLista = this.solicitacaoExames;
        return solicitacaoExamesLista;
    }

    public void setSolicitacaoExames(Set<ItemSolicitacaoExameDTO> solicitacaoExames) {
        this.solicitacaoExames = Collections.unmodifiableSet(solicitacaoExames);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SolicitacaoExameCompletoDTO that = (SolicitacaoExameCompletoDTO) o;
        return Objects.equals(solicitacaoExames, that.solicitacaoExames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), solicitacaoExames);
    }
}
