package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.TipoLancamento;
import br.com.basis.suprimentos.service.dto.TipoLancamentoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TipoLancamentoMapper extends EntityMapper<TipoLancamentoDTO, TipoLancamento> {


    default TipoLancamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoLancamento tipoLancamento = new TipoLancamento();
        tipoLancamento.setId(id);
        return tipoLancamento;
    }
}
