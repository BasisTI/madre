package br.com.basis.suprimentos.service.mapper;


import br.com.basis.suprimentos.domain.InformacaoTransferencia;
import br.com.basis.suprimentos.service.dto.InformacaoTransferenciaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface InformacaoTransferenciaMapper extends EntityMapper<InformacaoTransferenciaDTO, InformacaoTransferencia> {
    @Override
    @Mapping(source = "classificacaoMaterialId", target = "classificacaoMaterial.id")
    InformacaoTransferencia toEntity(InformacaoTransferenciaDTO dto);

    @Override
    @Mapping(source = "classificacaoMaterial.id", target = "classificacaoMaterialId")
    InformacaoTransferenciaDTO toDto(InformacaoTransferencia entity);

    default InformacaoTransferencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        InformacaoTransferencia informacaoTransferencia = new InformacaoTransferencia();
        informacaoTransferencia.setId(id);
        return informacaoTransferencia;
    }
}
