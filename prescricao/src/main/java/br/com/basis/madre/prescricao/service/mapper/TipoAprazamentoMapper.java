package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.TipoAprazamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoAprazamento} and its DTO {@link TipoAprazamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoAprazamentoMapper extends EntityMapper<TipoAprazamentoDTO, TipoAprazamento> {


    @Mapping(target = "itemPrescricaoDietas", ignore = true)
    @Mapping(target = "removeItemPrescricaoDieta", ignore = true)
    TipoAprazamento toEntity(TipoAprazamentoDTO tipoAprazamentoDTO);

    default TipoAprazamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoAprazamento tipoAprazamento = new TipoAprazamento();
        tipoAprazamento.setId(id);
        return tipoAprazamento;
    }
}
