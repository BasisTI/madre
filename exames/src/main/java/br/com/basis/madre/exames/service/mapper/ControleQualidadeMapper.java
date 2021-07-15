package br.com.basis.madre.exames.service.mapper;


import br.com.basis.madre.exames.domain.*;
import br.com.basis.madre.exames.service.dto.ControleQualidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ControleQualidade} and its DTO {@link ControleQualidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ControleQualidadeMapper extends EntityMapper<ControleQualidadeDTO, ControleQualidade> {



    default ControleQualidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        ControleQualidade controleQualidade = new ControleQualidade();
        controleQualidade.setId(id);
        return controleQualidade;
    }
}
