package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.AlaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ala} and its DTO {@link AlaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnidadeMapper.class})
public interface AlaMapper extends EntityMapper<AlaDTO, Ala> {

    @Mapping(source = "unidade.id", target = "unidadeId")
    AlaDTO toDto(Ala ala);

    @Mapping(source = "unidadeId", target = "unidade")
    Ala toEntity(AlaDTO alaDTO);

    default Ala fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ala ala = new Ala();
        ala.setId(id);
        return ala;
    }
}
