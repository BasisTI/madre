package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.SolicitacaoDeInternacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SolicitacaoDeInternacao and its DTO SolicitacaoDeInternacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {CIDMapper.class})
public interface SolicitacaoDeInternacaoMapper extends EntityMapper<SolicitacaoDeInternacaoDTO, SolicitacaoDeInternacao> {

    @Mapping(source = "cidPrincipal.id", target = "cidPrincipalId")
    @Mapping(source = "cidSecundario.id", target = "cidSecundarioId")
    SolicitacaoDeInternacaoDTO toDto(SolicitacaoDeInternacao solicitacaoDeInternacao);

    @Mapping(source = "cidPrincipalId", target = "cidPrincipal")
    @Mapping(source = "cidSecundarioId", target = "cidSecundario")
    @Mapping(target = "equipes", ignore = true)
    @Mapping(target = "crms", ignore = true)
    @Mapping(target = "procedimentos", ignore = true)
    SolicitacaoDeInternacao toEntity(SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO);

    default SolicitacaoDeInternacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolicitacaoDeInternacao solicitacaoDeInternacao = new SolicitacaoDeInternacao();
        solicitacaoDeInternacao.setId(id);
        return solicitacaoDeInternacao;
    }
}
