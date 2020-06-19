package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstoqueAlmoxarifado} and its DTO {@link EstoqueAlmoxarifadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlmoxarifadoMapper.class, MaterialMapper.class, FornecedorMapper.class, SolicitacaoComprasMapper.class})
public interface EstoqueAlmoxarifadoMapper extends EntityMapper<EstoqueAlmoxarifadoDTO, EstoqueAlmoxarifado> {

    @Mapping(source = "almoxarifado.id", target = "almoxarifadoId")
    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "fornecedor.id", target = "fornecedorId")
    @Mapping(source = "solicitacaoCompras.id", target = "solicitacaoComprasId")
    EstoqueAlmoxarifadoDTO toDto(EstoqueAlmoxarifado estoqueAlmoxarifado);

    @Mapping(target = "lotes", ignore = true)
    @Mapping(target = "removeLotes", ignore = true)
    @Mapping(source = "almoxarifadoId", target = "almoxarifado")
    @Mapping(source = "materialId", target = "material")
    @Mapping(source = "fornecedorId", target = "fornecedor")
    @Mapping(source = "solicitacaoComprasId", target = "solicitacaoCompras")
    EstoqueAlmoxarifado toEntity(EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO);

    default EstoqueAlmoxarifado fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstoqueAlmoxarifado estoqueAlmoxarifado = new EstoqueAlmoxarifado();
        estoqueAlmoxarifado.setId(id);
        return estoqueAlmoxarifado;
    }
}
