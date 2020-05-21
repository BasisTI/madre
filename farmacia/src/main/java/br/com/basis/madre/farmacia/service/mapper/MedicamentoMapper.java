package br.com.basis.madre.farmacia.service.mapper;

import br.com.basis.madre.farmacia.domain.*;
import br.com.basis.madre.farmacia.service.dto.MedicamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medicamento} and its DTO {@link MedicamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoMedicamentoMapper.class, ApresentacaoMapper.class, UnidadeMapper.class})
public interface MedicamentoMapper extends EntityMapper<MedicamentoDTO, Medicamento> {

    @Mapping(source = "tipoMedicamento.id", target = "tipoMedicamentoId")
    @Mapping(source = "apresentacao.id", target = "apresentacaoId")
    @Mapping(source = "unidade.id", target = "unidadeId")
    MedicamentoDTO toDto(Medicamento medicamento);

    @Mapping(source = "tipoMedicamentoId", target = "tipoMedicamento")
    @Mapping(source = "apresentacaoId", target = "apresentacao")
    @Mapping(source = "unidadeId", target = "unidade")
    Medicamento toEntity(MedicamentoDTO medicamentoDTO);

    default Medicamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medicamento medicamento = new Medicamento();
        medicamento.setId(id);
        return medicamento;
    }
}
