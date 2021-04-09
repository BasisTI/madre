package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.DDD;
import br.com.basis.madre.service.dto.DddDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link br.com.basis.madre.domain.DDD} and its DTO {@link br.com.basis.madre.service.dto.DDD_DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DDDMapper {

    DddDto toDto(DDD ddd);

    DDD toEntity(DddDto dddDTO);

    default DDD fromId(Long id) {
        if (id == null) {
            return null;
        }
        DDD ddd = new DDD();
        ddd.setId(id);
        return ddd;
    }
}
