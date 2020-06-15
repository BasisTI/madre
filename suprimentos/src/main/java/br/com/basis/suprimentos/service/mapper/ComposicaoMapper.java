package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.Composicao;
import br.com.basis.suprimentos.service.dto.ComposicaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CaracteristicaArmazenamentoMapper.class})
public interface ComposicaoMapper extends EntityMapper<ComposicaoDTO, Composicao> {

    @Mapping(source = "caracteristicaArmazenamento.id", target = "caracteristicaArmazenamentoId")
    ComposicaoDTO toDto(Composicao composicao);

    @Mapping(source = "caracteristicaArmazenamentoId", target = "caracteristicaArmazenamento")
    Composicao toEntity(ComposicaoDTO composicaoDTO);

    default Composicao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Composicao composicao = new Composicao();
        composicao.setId(id);
        return composicao;
    }
}
