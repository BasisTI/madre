package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.SituacaoDeLeito;
import br.com.basis.madre.service.dto.SituacaoDeLeitoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SituacaoDeLeitoMapper extends EntityMapper<SituacaoDeLeitoDTO, SituacaoDeLeito> {


    default SituacaoDeLeito fromId(Long id) {
        if (id == null) {
            return null;
        }
        SituacaoDeLeito situacaoDeLeito = new SituacaoDeLeito();
        situacaoDeLeito.setId(id);
        return situacaoDeLeito;
    }
}
