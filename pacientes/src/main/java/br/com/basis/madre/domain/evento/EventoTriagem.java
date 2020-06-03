package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Triagem;

public class EventoTriagem {
    private Triagem triagem;

    public EventoTriagem(Triagem triagem) {
        this.triagem = triagem;
    }

    public Triagem getTriagem() {
        return triagem;
    }

    public void setTriagem(Triagem triagem) {
        this.triagem = triagem;
    }
}
