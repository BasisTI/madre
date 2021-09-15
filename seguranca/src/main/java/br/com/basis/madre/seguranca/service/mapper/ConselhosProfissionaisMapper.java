package br.com.basis.madre.seguranca.service.mapper;



import br.com.basis.madre.seguranca.domain.ConselhosProfissionais;
import br.com.basis.madre.seguranca.service.dto.ConselhosProfissionaisDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper for the entity {@link ConselhosProfissionais} and its DTO {@link ConselhosProfissionaisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConselhosProfissionaisMapper extends EntityMapper<ConselhosProfissionaisDTO, ConselhosProfissionais> {


    @Mapping(target = "tiposDeQualificacaos", ignore = true)
    @Mapping(target = "removeTiposDeQualificacao", ignore = true)
    ConselhosProfissionais toEntity(ConselhosProfissionaisDTO conselhosProfissionaisDTO);

    default ConselhosProfissionais fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConselhosProfissionais conselhosProfissionais = new ConselhosProfissionais();
        conselhosProfissionais.setId(id);
        return conselhosProfissionais;
    }
}
