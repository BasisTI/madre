package br.com.basis.madre.farmacia.service.mapper;

import br.com.basis.madre.farmacia.domain.*;
import br.com.basis.madre.farmacia.service.dto.EstornoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estorno} and its DTO {@link EstornoDTO}.
 */
@Mapper(componentModel = "spring", uses = {DispensacaoMedicamentosMapper.class, MotivoMapper.class})
public interface EstornoMapper extends EntityMapper<EstornoDTO, Estorno> {

    @Mapping(source = "dispensacaoMedicamentos.id", target = "dispensacaoMedicamentosId")
    @Mapping(source = "motivo.id", target = "motivoId")
    EstornoDTO toDto(Estorno estorno);

    @Mapping(source = "dispensacaoMedicamentosId", target = "dispensacaoMedicamentos")
    @Mapping(source = "motivoId", target = "motivo")
    Estorno toEntity(EstornoDTO estornoDTO);

    default Estorno fromId(Long id) {
        if (id == null) {
            return null;
        }
        Estorno estorno = new Estorno();
        estorno.setId(id);
        return estorno;
    }
}
