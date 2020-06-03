package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.domain.enumeration.TipoDeMutacao;
import lombok.Data;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
public class EventoTriagem implements Serializable {
    private Triagem triagem;
    private ZonedDateTime dataDeLancamento = ZonedDateTime.now(ZoneId.systemDefault());
    private TipoDeMutacao tipoDoEvento;

    public EventoTriagem triagem(Triagem triagem) {
        this.triagem = triagem;

        return this;
    }

    public EventoTriagem tipoDoEvento(TipoDeMutacao tipoDeMutacao) {
        tipoDoEvento = tipoDeMutacao;

        return this;
    }
}
