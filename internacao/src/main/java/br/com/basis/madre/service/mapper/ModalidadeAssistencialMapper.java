package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.ModalidadeAssistencial;
import br.com.basis.madre.service.dto.ModalidadeAssistencialDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ModalidadeAssistencialMapper extends
    EntityMapper<ModalidadeAssistencialDTO, ModalidadeAssistencial> {

    default ModalidadeAssistencial fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModalidadeAssistencial modalidadeAssistencial = new ModalidadeAssistencial();
        modalidadeAssistencial.setId(id);
        return modalidadeAssistencial;
    }

}
