package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoProcedimentoEspecialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemPrescricaoProcedimentoEspecial} and its DTO {@link ItemPrescricaoProcedimentoEspecialDTO}.
 */
@Mapper(componentModel = "spring", uses = {EspeciaisDiversosMapper.class, CirurgiasLeitoMapper.class, OrteseProteseMapper.class, PrescricaoProcedimentoEspecialMapper.class})
public interface ItemPrescricaoProcedimentoEspecialMapper extends EntityMapper<ItemPrescricaoProcedimentoEspecialDTO, ItemPrescricaoProcedimentoEspecial> {

    @Mapping(source = "especiaisDiversos.id", target = "especiaisDiversosId")
    @Mapping(source = "cirurgiasLeito.id", target = "cirurgiasLeitoId")
    @Mapping(source = "orteseProtese.id", target = "orteseProteseId")
    @Mapping(source = "prescricaoProcedimentoEspecial.id", target = "prescricaoProcedimentoEspecialId")
    ItemPrescricaoProcedimentoEspecialDTO toDto(ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial);

    @Mapping(source = "especiaisDiversosId", target = "especiaisDiversos")
    @Mapping(source = "cirurgiasLeitoId", target = "cirurgiasLeito")
    @Mapping(source = "orteseProteseId", target = "orteseProtese")
    @Mapping(source = "prescricaoProcedimentoEspecialId", target = "prescricaoProcedimentoEspecial")
    ItemPrescricaoProcedimentoEspecial toEntity(ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO);

    default ItemPrescricaoProcedimentoEspecial fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial = new ItemPrescricaoProcedimentoEspecial();
        itemPrescricaoProcedimentoEspecial.setId(id);
        return itemPrescricaoProcedimentoEspecial;
    }
}
