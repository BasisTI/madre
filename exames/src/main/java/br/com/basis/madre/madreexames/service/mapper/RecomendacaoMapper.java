package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.Recomendacao;
import br.com.basis.madre.madreexames.service.dto.RecomendacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Recomendacao} and its DTO {@link RecomendacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {MaterialMapper.class})
public interface RecomendacaoMapper extends EntityMapper<RecomendacaoDTO, Recomendacao> {

    @Mapping(source = "material.id", target = "materialId")
    RecomendacaoDTO toDto(Recomendacao recomendacao);

    @Mapping(source = "materialId", target = "material")
    Recomendacao toEntity(RecomendacaoDTO recomendacaoDTO);

    default Recomendacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recomendacao recomendacao = new Recomendacao();
        recomendacao.setId(id);
        return recomendacao;
    }
}
