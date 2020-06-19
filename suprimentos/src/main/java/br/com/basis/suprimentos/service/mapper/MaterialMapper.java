package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.MaterialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Material} and its DTO {@link MaterialDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnidadeMedidaMapper.class, GrupoMaterialMapper.class, AlmoxarifadoMapper.class, CodigoCatmatMapper.class, SazonalidadeMapper.class, TipoResiduoMapper.class, OrigemParecerTecnicoMapper.class})
public interface MaterialMapper extends EntityMapper<MaterialDTO, Material> {

    @Mapping(source = "unidadeMedida.id", target = "unidadeMedidaId")
    @Mapping(source = "grupo.id", target = "grupoId")
    @Mapping(source = "localEstoque.id", target = "localEstoqueId")
    @Mapping(source = "codigoCatmat.id", target = "codigoCatmatId")
    @Mapping(source = "sazonalidade.id", target = "sazonalidadeId")
    @Mapping(source = "tipoResiduo.id", target = "tipoResiduoId")
    @Mapping(source = "origemParecerTecnico.id", target = "origemParecerTecnicoId")
    MaterialDTO toDto(Material material);

    @Mapping(source = "unidadeMedidaId", target = "unidadeMedida")
    @Mapping(source = "grupoId", target = "grupo")
    @Mapping(source = "localEstoqueId", target = "localEstoque")
    @Mapping(source = "codigoCatmatId", target = "codigoCatmat")
    @Mapping(source = "sazonalidadeId", target = "sazonalidade")
    @Mapping(source = "tipoResiduoId", target = "tipoResiduo")
    @Mapping(source = "origemParecerTecnicoId", target = "origemParecerTecnico")
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
