package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.Fornecedor;
import br.com.basis.suprimentos.service.dto.FornecedorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface FornecedorMapper extends EntityMapper<FornecedorDTO, Fornecedor> {
    default Fornecedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        return fornecedor;
    }
}
