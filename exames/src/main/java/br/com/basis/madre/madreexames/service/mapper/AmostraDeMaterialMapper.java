package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.AmostraDeMaterial;
import br.com.basis.madre.madreexames.service.dto.AmostraDeMaterialDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AmostraDeMaterial} and its DTO {@link AmostraDeMaterialDTO}.
 */
@Mapper(componentModel = "spring", uses = {RecipienteMapper.class, AnticoagulanteMapper.class, MaterialMapper.class, MaterialDeExameMapper.class})
public interface AmostraDeMaterialMapper extends EntityMapper<AmostraDeMaterialDTO, AmostraDeMaterial> {

    @Mapping(source = "amostraRecipiente.id", target = "amostraRecipienteId")
    @Mapping(source = "amostraRecipiente.nome", target = "amostraRecipienteNome")
    @Mapping(source = "amostraAnticoagulante.id", target = "amostraAnticoagulanteId")
    @Mapping(source = "amostraAnticoagulante.nome", target = "amostraAnticoagulanteNome")
    @Mapping(source = "amostraMaterial.id", target = "amostraMaterialId")
    @Mapping(source = "amostraMaterial.nome", target = "amostraMaterialNome")
    @Mapping(source = "materialDeExame.id", target = "materialDeExameId")
    AmostraDeMaterialDTO toDto(AmostraDeMaterial amostraDeMaterial);

    @Mapping(source = "amostraRecipienteId", target = "amostraRecipiente")
    @Mapping(source = "amostraAnticoagulanteId", target = "amostraAnticoagulante")
    @Mapping(source = "amostraMaterialId", target = "amostraMaterial")
    @Mapping(source = "materialDeExameId", target = "materialDeExame")
    AmostraDeMaterial toEntity(AmostraDeMaterialDTO amostraDeMaterialDTO);

    default AmostraDeMaterial fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmostraDeMaterial amostraDeMaterial = new AmostraDeMaterial();
        amostraDeMaterial.setId(id);
        return amostraDeMaterial;
    }
}
