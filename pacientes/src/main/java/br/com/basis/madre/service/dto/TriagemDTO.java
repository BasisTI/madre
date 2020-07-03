package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class TriagemDTO implements Serializable {

    private Long id;

    @NotNull
    private ClassificacaoDeRisco classificacaoDeRisco;

    private BigDecimal pressaoArterial;

    private BigDecimal frequenciaCardiaca;

    private BigDecimal temperatura;

    private BigDecimal peso;

    private String sinaisSintomas;

    private ZonedDateTime dataHoraDoAtendimento;


    @NotNull
    private String descricaoQueixa;

    private Boolean vitimaDeAcidente;

    private Boolean removidoDeAmbulancia;

    private String observacao;

    private Long pacienteId;


}
