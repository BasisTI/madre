package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.Diluente;
import br.com.basis.madre.prescricao.service.dto.DiluenteDTO;

/**
 * Mapper for the entity {@link Diluente} and its DTO {@link DiluenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiluenteMapper extends EntityMapper<DiluenteDTO, Diluente> {


	@Mapping(source="id", target="id")
	@Mapping(source="descricao", target="descricao")
    Diluente toEntity(DiluenteDTO diluenteDTO);

	@Mapping(source="id", target="id")
	@Mapping(source="descricao", target="descricao")
	DiluenteDTO toDto(Diluente diluente);
    default Diluente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diluente diluente = new Diluente();
        diluente.setId(id);
        return diluente;
    }
}
