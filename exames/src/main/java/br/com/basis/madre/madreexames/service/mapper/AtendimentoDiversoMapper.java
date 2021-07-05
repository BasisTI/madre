package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.AtendimentoDiversoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AtendimentoDiverso} and its DTO {@link AtendimentoDiversoDTO}.
 */
@Mapper(componentModel = "spring", uses = {InformacoesComplementaresMapper.class, ProjetoDePesquisaMapper.class, LaboratorioExternoMapper.class, ControleQualidadeMapper.class, CadaverMapper.class})
public interface AtendimentoDiversoMapper extends EntityMapper<AtendimentoDiversoDTO, AtendimentoDiverso> {

    @Mapping(source = "informacoes.id", target = "informacoesId")
    @Mapping(source = "informacoes.codigo", target = "informacoesCodigo")
    @Mapping(source = "projeto.id", target = "projetoId")
    @Mapping(source = "projeto.nome", target = "projetoNome")
    @Mapping(source = "laboratorio.id", target = "laboratorioId")
    @Mapping(source = "laboratorio.nome", target = "laboratorioNome")
    @Mapping(source = "controle.id", target = "controleId")
    @Mapping(source = "controle.codigo", target = "controleCodigo")
    @Mapping(source = "cadaver.id", target = "cadaverId")
    @Mapping(source = "cadaver.nome", target = "cadaverNome")
    AtendimentoDiversoDTO toDto(AtendimentoDiverso atendimentoDiverso);

    @Mapping(source = "informacoesId", target = "informacoes")
    @Mapping(source = "projetoId", target = "projeto")
    @Mapping(source = "laboratorioId", target = "laboratorio")
    @Mapping(source = "controleId", target = "controle")
    @Mapping(source = "cadaverId", target = "cadaver")
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
