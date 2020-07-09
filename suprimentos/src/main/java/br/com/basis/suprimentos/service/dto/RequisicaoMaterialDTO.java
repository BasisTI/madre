package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class RequisicaoMaterialDTO implements Serializable {
    private Long id;

    private Long caRequisitanteId;

    private Long caAplicacaoId;

    @NotNull
    private ZonedDateTime geradoEm;

    @NotNull
    private String geradoPor;

    private ZonedDateTime confirmadoEm;

    private String confirmadoPor;

    private Long situacaoId;
}
