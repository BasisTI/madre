package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.OrigemDaInternacao;
import br.com.basis.madre.service.dto.OrigemDaInternacaoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrigemDaInternacaoMapper extends
    EntityMapper<OrigemDaInternacaoDTO, OrigemDaInternacao> {

    default OrigemDaInternacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrigemDaInternacao origemDaInternacao = new OrigemDaInternacao();
        origemDaInternacao.setId(id);
        return origemDaInternacao;
    }

}
