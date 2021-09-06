package br.com.basis.madre.madreexames.service.dto;

import java.util.Collections;
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
}
