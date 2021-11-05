package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.Pagination;
import br.com.basis.madre.service.dto.PaginationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link br.com.basis.madre.domain.Pagination} and its DTO {@link br.com.basis.madre.service.dto.PaginationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaginationMapper extends EntityMapper<PaginationDTO, Pagination> {

    PaginationDTO toDto(Pagination ddd);

    Pagination toEntity(PaginationDTO dddDTO);

}
