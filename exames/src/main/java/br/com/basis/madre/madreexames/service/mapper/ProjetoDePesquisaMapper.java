package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.ProjetoDePesquisaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjetoDePesquisa} and its DTO {@link ProjetoDePesquisaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjetoDePesquisaMapper extends EntityMapper<ProjetoDePesquisaDTO, ProjetoDePesquisa> {



    default ProjetoDePesquisa fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjetoDePesquisa projetoDePesquisa = new ProjetoDePesquisa();
        projetoDePesquisa.setId(id);
        return projetoDePesquisa;
    }
}
