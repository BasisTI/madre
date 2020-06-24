package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Ocupacao;
import br.com.basis.madre.service.dto.OcupacaoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Ocupacao} and its DTO {@link OcupacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OcupacaoMapper extends EntityMapper<OcupacaoDTO, Ocupacao> {



    default Ocupacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ocupacao ocupacao = new Ocupacao();
        ocupacao.setId(id);
        return ocupacao;
    }
}
