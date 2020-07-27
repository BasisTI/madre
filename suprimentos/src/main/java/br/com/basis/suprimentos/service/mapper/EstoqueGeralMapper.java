package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.EstoqueGeral;
import br.com.basis.suprimentos.service.dto.EstoqueGeralDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface EstoqueGeralMapper extends EntityMapper<EstoqueGeralDTO, EstoqueGeral> {
    @Override
    @Mapping(source = "material.id", target = "materialId")
    EstoqueGeralDTO toDto(EstoqueGeral estoqueGeral);

    @Override
    @Mapping(source = "materialId", target = "material.id")
    EstoqueGeral toEntity(EstoqueGeralDTO dto);

    default EstoqueGeral fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstoqueGeral estoqueGeral = new EstoqueGeral();
        estoqueGeral.setId(id);
        return estoqueGeral;
    }
}
