package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.Material;
import br.com.basis.madre.madreexames.service.dto.MaterialDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Material} and its DTO {@link MaterialDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MaterialMapper extends EntityMapper<MaterialDTO, Material> {


    @Mapping(target = "materials", ignore = true)
    @Mapping(target = "removeMaterial", ignore = true)
    Material toEntity(MaterialDTO materialDTO);

    default Material fromId(Long id) {
        if (id == null) {
            return null;
        }
        Material material = new Material();
        material.setId(id);
        return material;
    }
}
