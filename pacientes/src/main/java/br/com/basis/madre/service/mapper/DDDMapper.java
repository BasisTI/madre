package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.DDD;
import br.com.basis.madre.service.dto.DDD_DTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link br.com.basis.madre.domain.DDD} and its DTO {@link br.com.basis.madre.service.dto.DDD_DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DDDMapper {

    DDD_DTO toDto(DDD ddd);

    DDD toEntity(DDD_DTO dddDTO);

    default DDD fromId(Long id) {
        if (id == null) {
            return null;
        }
        DDD ddd = new DDD();
        ddd.setId(id);
        return ddd;
    }
}
