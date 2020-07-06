package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class TransferenciaAlmoxarifadoDTO implements Serializable {
    private Long id;

    private Long origemId;

    private Long destinoId;

    private String geradoPor;

    private Set<ItemTransferenciaDTO> itens = new HashSet<>();

    private ZonedDateTime geradoEm;

    private InformacaoTransferenciaDTO informacaoTransferencia;
}
