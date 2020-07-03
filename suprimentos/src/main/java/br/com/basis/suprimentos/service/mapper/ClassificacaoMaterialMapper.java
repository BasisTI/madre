package br.com.basis.suprimentos.service.mapper;


import br.com.basis.suprimentos.domain.ClassificacaoMaterial;
import br.com.basis.suprimentos.service.dto.ClassificacaoMaterialDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ClassificacaoMaterialMapper extends EntityMapper<ClassificacaoMaterialDTO, ClassificacaoMaterial> {
    default ClassificacaoMaterial fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassificacaoMaterial classificacaoMaterial = new ClassificacaoMaterial();
        classificacaoMaterial.setId(id);
        return classificacaoMaterial;
    }
}
