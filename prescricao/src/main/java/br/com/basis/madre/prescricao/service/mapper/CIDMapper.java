package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.CID;
import br.com.basis.madre.prescricao.service.dto.CidDTO;

@Mapper(componentModel = "spring", uses = {})
public interface CIDMapper {
	
	@Mapping(source="id", target="id")
	@Mapping(source="codigo", target="codigo")
	@Mapping(source="descricao", target="descricao")
	CID toEntity(CidDTO cidDTO);
	
	@Mapping(source="id", target="id")
	@Mapping(source="codigo", target="codigo")
	@Mapping(source="descricao", target="descricao")
	CidDTO toDto(CID cid);

    default CID fromId(Long id) {
        if (id == null) {
            return null;
        }
        CID cid = new CID();
        cid.setId(id);
        return cid;
    }

}
