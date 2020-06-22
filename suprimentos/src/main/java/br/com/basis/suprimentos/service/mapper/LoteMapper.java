package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.LoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lote} and its DTO {@link LoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {MarcaComercialMapper.class, EstoqueAlmoxarifadoMapper.class})
public interface LoteMapper extends EntityMapper<LoteDTO, Lote> {

    @Mapping(source = "marcaComercial.id", target = "marcaComercialId")
    @Mapping(source = "estoque.id", target = "estoqueId")
    LoteDTO toDto(Lote lote);

    @Mapping(source = "marcaComercialId", target = "marcaComercial")
    @Mapping(source = "estoqueId", target = "estoque")
    Lote toEntity(LoteDTO loteDTO);

    default Lote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lote lote = new Lote();
        lote.setId(id);
        return lote;
    }
}
