package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.service.dto.GradeAgendamentoExameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeraHorariosGradeService {

    public void buscarDiasCompativeis(GradeAgendamentoExameDTO gradeAgendamentoExameDTO) {


        List<Long> comparacao = new ArrayList<>();

        if (gradeAgendamentoExameDTO.getDias().toString().contains("SEGUNDA")) {
            comparacao.add(1L);
        }
        if (gradeAgendamentoExameDTO.getDias().toString().contains("TERCA")) {
            comparacao.add(2L);
        }
        if (gradeAgendamentoExameDTO.getDias().toString().contains("QUARTA")) {
            comparacao.add(3L);
        }
        if (gradeAgendamentoExameDTO.getDias().toString().contains("QUINTA")) {
            comparacao.add(4L);
        }
        if (gradeAgendamentoExameDTO.getDias().toString().contains("SEXTA")) {
            comparacao.add(5L);
        }
        if (gradeAgendamentoExameDTO.getDias().toString().contains("SABADO")) {
            comparacao.add(6L);
        }
        if (gradeAgendamentoExameDTO.getDias().toString().contains("DOMINGO")) {
            comparacao.add(7L);
        }

        LocalDate dataInicioCopia = gradeAgendamentoExameDTO.getDataInicio();

        while (!gradeAgendamentoExameDTO.getDataInicio().isAfter(gradeAgendamentoExameDTO.getDataFim())) {
            Long numeroComparacao;

            for (Long nDias: comparacao) {
                numeroComparacao = nDias;

                if (gradeAgendamentoExameDTO.getDataInicio().getDayOfWeek().getValue() == numeroComparacao) {
                    System.err.println("Dia: " + gradeAgendamentoExameDTO.getDataInicio()
                        .getDayOfWeek() + " em: " + gradeAgendamentoExameDTO.getDataInicio());
                }
            }
            gradeAgendamentoExameDTO.setDataInicio(gradeAgendamentoExameDTO.getDataInicio().plus(1, ChronoUnit.DAYS));

        }

        System.err.println(comparacao);

        gradeAgendamentoExameDTO.setDataInicio(dataInicioCopia);
    }

}
