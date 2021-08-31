package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.InstituicaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Instituicao} and its DTO {@link InstituicaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConselhosProfissionaisMapper.class})
public interface InstituicaoMapper extends EntityMapper<InstituicaoDTO, Instituicao> {

    @Mapping(source = "conselhosProfissionais.id", target = "conselhosProfissionaisId")
    InstituicaoDTO toDto(Instituicao instituicao);

    @Mapping(target = "graduacaoInstituicaos", ignore = true)
    @Mapping(target = "removeGraduacaoInstituicao", ignore = true)
    @Mapping(source = "conselhosProfissionaisId", target = "conselhosProfissionais")
    Instituicao toEntity(InstituicaoDTO instituicaoDTO);

    default Instituicao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Instituicao instituicao = new Instituicao();
        instituicao.setId(id);
        return instituicao;
    }
}
