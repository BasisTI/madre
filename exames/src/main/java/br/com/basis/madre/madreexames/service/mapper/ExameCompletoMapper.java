package br.com.basis.madre.madreexames.service.mapper;

import br.com.basis.madre.madreexames.domain.Exame;
import br.com.basis.madre.madreexames.service.dto.ExameCompletoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ExameMapper.class})
public interface ExameCompletoMapper extends EntityMapper<ExameCompletoDTO, Exame> {

    Exame toEntity(ExameCompletoDTO exameCompletoDTO);

    default Exame fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exame exame = new Exame();
        exame.setId(id);
        return exame;
    }
}
