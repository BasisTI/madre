package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InformacaoTransferenciaDTO implements Serializable {
    private Long id;
    private Boolean ativa;
    private Boolean efetivada;
    private Long classificacaoMaterialId;
    private Long centroDeAtividadeId;
}
