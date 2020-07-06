package br.com.basis.suprimentos.service.mapper;


import br.com.basis.suprimentos.domain.InformacaoTransferencia;
import br.com.basis.suprimentos.service.dto.InformacaoTransferenciaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface InformacaoTransferenciaMapper extends EntityMapper<InformacaoTransferenciaDTO, InformacaoTransferencia> {
    default InformacaoTransferencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        InformacaoTransferencia informacaoTransferencia = new InformacaoTransferencia();
        informacaoTransferencia.setId(id);
        return informacaoTransferencia;
    }
}
