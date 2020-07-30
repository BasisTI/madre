package br.com.basis.madre.farmacia.service.mapper;

import br.com.basis.madre.farmacia.domain.*;
import br.com.basis.madre.farmacia.service.dto.DispensacaoMedicamentosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DispensacaoMedicamentos} and its DTO {@link DispensacaoMedicamentosDTO}.
 */
@Mapper(componentModel = "spring", uses = {DispensacaoMapper.class, MedicamentoMapper.class})
public interface DispensacaoMedicamentosMapper extends EntityMapper<DispensacaoMedicamentosDTO, DispensacaoMedicamentos> {

    @Mapping(source = "dispensacao.id", target = "dispensacaoId")
    @Mapping(source = "medicamentos", target = "medicamentosId")
    @Mapping(source = "estornado", target = "estornado")
    DispensacaoMedicamentosDTO toDto(DispensacaoMedicamentos dispensacaoMedicamentos);

    @Mapping(source = "dispensacaoId", target = "dispensacao")
    @Mapping(source = "medicamentosId", target = "medicamentos")
    @Mapping(source = "estornado", target = "estornado")
    DispensacaoMedicamentos toEntity(DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO);

    default DispensacaoMedicamentos fromId(Long id) {
        if (id == null) {
            return null;
        }
        DispensacaoMedicamentos dispensacaoMedicamentos = new DispensacaoMedicamentos();
        dispensacaoMedicamentos.setId(id);
        return dispensacaoMedicamentos;
    }
}
