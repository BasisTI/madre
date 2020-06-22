package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.GrupoMaterial;
import br.com.basis.suprimentos.service.dto.GrupoMaterialDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface GrupoMaterialMapper extends EntityMapper<GrupoMaterialDTO, GrupoMaterial> {
    default GrupoMaterial fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrupoMaterial grupoMaterial = new GrupoMaterial();
        grupoMaterial.setId(id);
        return grupoMaterial;
    }
}
