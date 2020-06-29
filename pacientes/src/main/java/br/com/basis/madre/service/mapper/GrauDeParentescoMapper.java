package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.GrauDeParentesco;
import br.com.basis.madre.service.dto.GrauDeParentescoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link GrauDeParentesco} and its DTO {@link GrauDeParentescoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GrauDeParentescoMapper extends EntityMapper<GrauDeParentescoDTO, GrauDeParentesco> {



    default GrauDeParentesco fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrauDeParentesco grauDeParentesco = new GrauDeParentesco();
        grauDeParentesco.setId(id);
        return grauDeParentesco;
    }
}
