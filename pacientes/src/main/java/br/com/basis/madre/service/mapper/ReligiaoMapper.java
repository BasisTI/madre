package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Religiao;
import br.com.basis.madre.service.dto.ReligiaoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Religiao} and its DTO {@link ReligiaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReligiaoMapper extends EntityMapper<ReligiaoDTO, Religiao> {



    default Religiao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Religiao religiao = new Religiao();
        religiao.setId(id);
        return religiao;
    }
}
