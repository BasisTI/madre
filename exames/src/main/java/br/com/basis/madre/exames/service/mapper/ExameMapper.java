package br.com.basis.madre.exames.service.mapper;


import br.com.basis.madre.exames.domain.*;
import br.com.basis.madre.exames.service.dto.ExameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exame} and its DTO {@link ExameDTO}.
 */
@Mapper(componentModel = "spring", uses = {MaterialMapper.class, TipoAmostraMapper.class})
public interface ExameMapper extends EntityMapper<ExameDTO, Exame> {

    @Mapping(source = "exame.id", target = "exameId")
    @Mapping(source = "exame.nome", target = "exameNome")
    @Mapping(source = "exame.id", target = "exameId")
    @Mapping(source = "exame.nome", target = "exameNome")
    ExameDTO toDto(Exame exame);

    @Mapping(source = "exameId", target = "exame")
    @Mapping(source = "exameId", target = "exame")
    Exame toEntity(ExameDTO exameDTO);

    default Exame fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exame exame = new Exame();
        exame.setId(id);
        return exame;
    }
}
