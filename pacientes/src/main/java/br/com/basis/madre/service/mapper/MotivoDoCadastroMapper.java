package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.MotivoDoCadastro;
import br.com.basis.madre.service.dto.MotivoDoCadastroDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link MotivoDoCadastro} and its DTO {@link MotivoDoCadastroDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MotivoDoCadastroMapper extends EntityMapper<MotivoDoCadastroDTO, MotivoDoCadastro> {



    default MotivoDoCadastro fromId(Long id) {
        if (id == null) {
            return null;
        }
        MotivoDoCadastro motivoDoCadastro = new MotivoDoCadastro();
        motivoDoCadastro.setId(id);
        return motivoDoCadastro;
    }
}
