package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.Sazonalidade;
import br.com.basis.suprimentos.service.dto.SazonalidadeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SazonalidadeMapper extends EntityMapper<SazonalidadeDTO, Sazonalidade> {
    default Sazonalidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sazonalidade sazonalidade = new Sazonalidade();
        sazonalidade.setId(id);
        return sazonalidade;
    }
}
