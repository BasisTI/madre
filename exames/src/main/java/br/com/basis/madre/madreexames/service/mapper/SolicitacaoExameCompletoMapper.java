package br.com.basis.madre.madreexames.service.mapper;

import br.com.basis.madre.madreexames.domain.SolicitacaoExame;
import br.com.basis.madre.madreexames.service.dto.SolicitacaoExameCompletoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ItemSolicitacaoExameMapper.class})
public interface SolicitacaoExameCompletoMapper extends EntityMapper<SolicitacaoExameCompletoDTO, SolicitacaoExame> {

    SolicitacaoExame toEntity(SolicitacaoExameCompletoDTO solicitacaoExameCompletoDTO);

    default SolicitacaoExame fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolicitacaoExame solicitacaoExame = new SolicitacaoExame();
        solicitacaoExame.setId(id);
        return solicitacaoExame;
    }
}
