package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.service.dto.LeitoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SituacaoDeLeitoMapper.class,
    UnidadeFuncionalMapper.class})
public interface LeitoMapper extends EntityMapper<LeitoDTO, Leito> {

    @Mapping(source = "situacao.id", target = "situacaoId")
    @Mapping(source = "unidadeFuncional.id", target = "unidadeFuncionalId")
    LeitoDTO toDto(Leito leito);

    @Mapping(source = "situacaoId", target = "situacao")
    @Mapping(source = "unidadeFuncionalId", target = "unidadeFuncional")
    Leito toEntity(LeitoDTO leitoDTO);

    default Leito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Leito leito = new Leito();
        leito.setId(id);
        return leito;
    }
}
