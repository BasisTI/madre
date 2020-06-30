package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Prescricao;
import br.com.basis.madre.service.dto.PrescricaoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Prescricao} and its DTO {@link PrescricaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrescricaoMapper extends EntityMapper<PrescricaoDTO, Prescricao> {



    default Prescricao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prescricao prescricao = new Prescricao();
        prescricao.setId(id);
        return prescricao;
    }
}
