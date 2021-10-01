package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.Pessoa;
import br.com.basis.madre.seguranca.service.dto.PessoasNaoCadastradasDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoasNaoCadastradasDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PessoasNaoCadastradasMapper extends EntityMapper<PessoasNaoCadastradasDTO, Pessoa> {

    default Pessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        return pessoa;
    }
}
