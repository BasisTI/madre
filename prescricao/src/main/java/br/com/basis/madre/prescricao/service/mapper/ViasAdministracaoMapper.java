package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.ViasAdministracaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ViasAdministracao} and its DTO {@link ViasAdministracaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViasAdministracaoMapper extends EntityMapper<ViasAdministracaoDTO, ViasAdministracao> {


    ViasAdministracao toEntity(ViasAdministracaoDTO viasAdministracaoDTO);

    default ViasAdministracao fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViasAdministracao viasAdministracao = new ViasAdministracao();
        viasAdministracao.setId(id);
        return viasAdministracao;
    }
}
