package br.com.basis.consulta.service.projection;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.swing.border.EmptyBorder;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CalendarioResumo {


        Long getId();
        LocalDate getDataHoraDaConsulta();
    String getNumeroSala();
    Enum getTurno();
    Enum getTipoPagador();


    }
