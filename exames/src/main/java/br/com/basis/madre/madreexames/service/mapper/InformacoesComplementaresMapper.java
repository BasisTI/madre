package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.InformacoesComplementaresDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InformacoesComplementares} and its DTO {@link InformacoesComplementaresDTO}.
 */
@Mapper(componentModel = "spring", uses = {AtendimentoDiversoMapper.class})
public interface InformacoesComplementaresMapper extends EntityMapper<InformacoesComplementaresDTO, InformacoesComplementares> {

    @Mapping(source = "atendimentoDiverso.id", target = "atendimentoDiversoId")
    InformacoesComplementaresDTO toDto(InformacoesComplementares informacoesComplementares);

    @Mapping(source = "atendimentoDiversoId", target = "atendimentoDiverso")
    InformacoesComplementares toEntity(InformacoesComplementaresDTO informacoesComplementaresDTO);

    default InformacoesComplementares fromId(Long id) {
        if (id == null) {
            return null;
        }
        InformacoesComplementares informacoesComplementares = new InformacoesComplementares();
        informacoesComplementares.setId(id);
        return informacoesComplementares;
    }
}
