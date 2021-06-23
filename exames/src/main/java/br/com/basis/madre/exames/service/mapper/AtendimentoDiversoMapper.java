package br.com.basis.madre.exames.service.mapper;


import br.com.basis.madre.exames.domain.*;
import br.com.basis.madre.exames.service.dto.AtendimentoDiversoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AtendimentoDiverso} and its DTO {@link AtendimentoDiversoDTO}.
 */
@Mapper(componentModel = "spring", uses = {InformacoesComplementaresMapper.class, ProjetoDePesquisaMapper.class, LaboratorioExternoMapper.class, ControleQualidadeMapper.class, CadaverMapper.class})
public interface AtendimentoDiversoMapper extends EntityMapper<AtendimentoDiversoDTO, AtendimentoDiverso> {

    @Mapping(source = "atendimentoDiverso.id", target = "atendimentoDiversoId")
    @Mapping(source = "atendimentoDiverso.infoClinica", target = "atendimentoDiversoInfoClinica")
    @Mapping(source = "atendimentoDiverso.id", target = "atendimentoDiversoId")
    @Mapping(source = "atendimentoDiverso.infoClinica", target = "atendimentoDiversoInfoClinica")
    @Mapping(source = "atendimentoDiverso.id", target = "atendimentoDiversoId")
    @Mapping(source = "atendimentoDiverso.infoClinica", target = "atendimentoDiversoInfoClinica")
    @Mapping(source = "atendimentoDiverso.id", target = "atendimentoDiversoId")
    @Mapping(source = "atendimentoDiverso.infoClinica", target = "atendimentoDiversoInfoClinica")
    @Mapping(source = "atendimentoDiverso.id", target = "atendimentoDiversoId")
    @Mapping(source = "atendimentoDiverso.infoClinica", target = "atendimentoDiversoInfoClinica")
    AtendimentoDiversoDTO toDto(AtendimentoDiverso atendimentoDiverso);

    @Mapping(source = "atendimentoDiversoId", target = "atendimentoDiverso")
    @Mapping(source = "atendimentoDiversoId", target = "atendimentoDiverso")
    @Mapping(source = "atendimentoDiversoId", target = "atendimentoDiverso")
    @Mapping(source = "atendimentoDiversoId", target = "atendimentoDiverso")
    @Mapping(source = "atendimentoDiversoId", target = "atendimentoDiverso")
    @Mapping(target = "atendimentoDiversos", ignore = true)
    @Mapping(target = "removeAtendimentoDiverso", ignore = true)
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
