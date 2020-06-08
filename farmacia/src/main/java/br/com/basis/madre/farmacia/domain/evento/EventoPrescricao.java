package br.com.basis.madre.farmacia.domain.evento;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class EventoPrescricao implements Serializable {
    private Parciente paciente;

    private ZonedDateTime dataDeLancamento;
}
