package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.ProcessoAssincronoGradeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessoAssincronoGrade} and its DTO {@link ProcessoAssincronoGradeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessoAssincronoGradeMapper extends EntityMapper<ProcessoAssincronoGradeDTO, ProcessoAssincronoGrade> {



    default ProcessoAssincronoGrade fromId(String id) {
        if (id == null) {
            return null;
        }
        ProcessoAssincronoGrade processoAssincronoGrade = new ProcessoAssincronoGrade();
        processoAssincronoGrade.setId(id);
        return processoAssincronoGrade;
    }
}
