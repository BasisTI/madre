package br.com.basis.suprimentos.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class ItemTransferenciaDTO implements Serializable {
    private Long id;

    private Long materialId;

    private Integer quantidadeEnviada;
}
