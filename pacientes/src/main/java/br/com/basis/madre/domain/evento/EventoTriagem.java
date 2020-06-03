package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Triagem;
import lombok.Data;

import java.io.Serializable;

@Data
public class EventoTriagem implements Serializable {
    private Triagem triagem;
}
