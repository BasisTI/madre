package br.com.basis.madre.farmacia.service.mapper;

import br.com.basis.madre.farmacia.domain.*;
import br.com.basis.madre.farmacia.service.dto.DispensacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dispensacao} and its DTO {@link DispensacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DispensacaoMapper extends EntityMapper<DispensacaoDTO, Dispensacao> {


    @Mapping(target = "dispensacaoMedicamentos", ignore = true)
    @Mapping(target = "removeDispensacaoMedicamentos", ignore = true)
    Dispensacao toEntity(DispensacaoDTO dispensacaoDTO);

    default Dispensacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dispensacao dispensacao = new Dispensacao();
        dispensacao.setId(id);
        return dispensacao;
    }
}
