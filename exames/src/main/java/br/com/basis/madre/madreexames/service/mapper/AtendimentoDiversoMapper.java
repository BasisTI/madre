package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.AtendimentoDiversoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AtendimentoDiverso} and its DTO {@link AtendimentoDiversoDTO}.
 */
@Mapper(componentModel = "spring", uses = {LaboratorioExternoMapper.class, ControleQualidadeMapper.class, CadaverMapper.class})
public interface AtendimentoDiversoMapper extends EntityMapper<AtendimentoDiversoDTO, AtendimentoDiverso> {

    @Mapping(source = "laboratorio.id", target = "laboratorioId")
    @Mapping(source = "laboratorio.nome", target = "laboratorioNome")
    @Mapping(source = "controle.id", target = "controleId")
    @Mapping(source = "controle.codigo", target = "controleCodigo")
    @Mapping(source = "cadaver.id", target = "cadaverId")
    @Mapping(source = "cadaver.nome", target = "cadaverNome")
    AtendimentoDiversoDTO toDto(AtendimentoDiverso atendimentoDiverso);

    @Mapping(source = "laboratorioId", target = "laboratorio")
    @Mapping(source = "controleId", target = "controle")
    @Mapping(source = "cadaverId", target = "cadaver")
    AtendimentoDiverso toEntity(AtendimentoDiversoDTO atendimentoDiversoDTO);

    default AtendimentoDiverso fromId(Long id) {
        if (id == null) {
            return null;
        }
        AtendimentoDiverso atendimentoDiverso = new AtendimentoDiverso();
        atendimentoDiverso.setId(id);
        return atendimentoDiverso;
    }
}
