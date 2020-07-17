package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class RequisicaoMaterialDTO implements Serializable {
    private Long id;

    private Long caRequisitanteId;

    private Long caAplicacaoId;

    private ZonedDateTime geradoEm;

    private String geradoPor;

    private ZonedDateTime confirmadoEm;

    private String confirmadoPor;

    private Long situacaoId;

    private Set<ItemRequisicaoDTO> itens = new HashSet<>();
}
