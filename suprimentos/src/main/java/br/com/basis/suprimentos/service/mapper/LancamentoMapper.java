package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.Lancamento;
import br.com.basis.suprimentos.service.dto.LancamentoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TipoLancamentoMapper.class})
public interface LancamentoMapper extends EntityMapper<LancamentoDTO, Lancamento> {

    @Mapping(source = "tipoLancamento.id", target = "tipoLancamentoId")
    LancamentoDTO toDto(Lancamento lancamento);

    @Mapping(source = "tipoLancamentoId", target = "tipoLancamento")
    Lancamento toEntity(LancamentoDTO lancamentoDTO);

    default Lancamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lancamento lancamento = new Lancamento();
        lancamento.setId(id);
        return lancamento;
    }
}
