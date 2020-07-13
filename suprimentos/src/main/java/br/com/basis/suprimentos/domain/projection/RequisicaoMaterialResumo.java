package br.com.basis.suprimentos.domain.projection;

import java.time.ZonedDateTime;

public interface RequisicaoMaterialResumo {
    Long getId();

    ZonedDateTime getGeradoEm();

    String getGeradoPor();

    _SituacaoRequisicao getSituacao();

    _CentroDeAtividade getCaRequisitante() ;

    _CentroDeAtividade getCaAplicacao() ;

    interface _CentroDeAtividade {
        Long getId();
        String getDescricao();
    }

    interface _SituacaoRequisicao {
        String getDescricao();
    }
}
