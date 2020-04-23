package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.CertidaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Certidao} and its DTO {@link CertidaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CertidaoMapper extends EntityMapper<CertidaoDTO, Certidao> {



    default Certidao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Certidao certidao = new Certidao();
        certidao.setId(id);
        return certidao;
    }
}
