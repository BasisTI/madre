package br.com.basis.madre.madreexames.service.dto;

import java.util.Set;

public class SolicitacaoExameCompletoDTO extends SolicitacaoExameDTO {

    private Set<ItemSolicitacaoExameDTO> solicitacaoExames;

    public Set<ItemSolicitacaoExameDTO> getSolicitacaoExames() {
        return solicitacaoExames;
    }

    public void setSolicitacaoExames(Set<ItemSolicitacaoExameDTO> solicitacaoExames) {
        this.solicitacaoExames = solicitacaoExames;
    }
}
