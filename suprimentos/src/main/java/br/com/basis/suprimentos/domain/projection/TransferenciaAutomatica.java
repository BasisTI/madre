package br.com.basis.suprimentos.domain.projection;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface TransferenciaAutomatica {
    Long getId();

    String getGeradoPor();

    ZonedDateTime getGeradoEm();

    _Almoxarifado getOrigem();

    _Almoxarifado getDestino();

    _InformacaoTransferencia getInformacaoTransferencia();

    interface _InformacaoTransferencia {
        _ClassificacaoMaterial getClassificacaoMaterial();

        interface _ClassificacaoMaterial {
            String getDescricao();
        }
    }

    interface _Almoxarifado {
        String getDescricao();
    }
}
