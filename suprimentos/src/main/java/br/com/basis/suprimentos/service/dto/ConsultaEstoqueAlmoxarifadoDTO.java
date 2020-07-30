package br.com.basis.suprimentos.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultaEstoqueAlmoxarifadoDTO implements Serializable {
    private Long almoxarifadoId;
    private Long fornecedorId;
    private Long materialId;
    private Long grupoId;
    private Long unidadeMedidaId;
    private Boolean estocavel;
    private Boolean ativo;
}
