package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.RequisicaoMaterial;
import br.com.basis.suprimentos.service.dto.RequisicaoMaterialDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SituacaoRequisicaoMapper.class})
public interface RequisicaoMaterialMapper extends EntityMapper<RequisicaoMaterialDTO, RequisicaoMaterial> {
    @Mapping(source = "situacao.id", target = "situacaoId")
    @Mapping(source = "caAplicacao.id", target = "caAplicacaoId")
    @Mapping(source = "caRequisitante.id", target = "caRequisitanteId")
    RequisicaoMaterialDTO toDto(RequisicaoMaterial requisicaoMaterial);

    @Mapping(target = "itens", ignore = true)
    @Mapping(source = "situacaoId", target = "situacao")
    @Mapping(source = "caRequisitanteId", target = "caRequisitante.id")
    @Mapping(source = "caAplicacaoId", target = "caAplicacao.id")
    RequisicaoMaterial toEntity(RequisicaoMaterialDTO requisicaoMaterialDTO);

    default RequisicaoMaterial fromId(Long id) {
        if (id == null) {
            return null;
        }
        RequisicaoMaterial requisicaoMaterial = new RequisicaoMaterial();
        requisicaoMaterial.setId(id);
        return requisicaoMaterial;
    }
}
