package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.CartaoSUSDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CartaoSUS} and its DTO {@link CartaoSUSDTO}.
 */
@Mapper(componentModel = "spring", uses = {JustificativaMapper.class, MotivoDoCadastroMapper.class})
public interface CartaoSUSMapper extends EntityMapper<CartaoSUSDTO, CartaoSUS> {

    @Mapping(source = "justificativa.id", target = "justificativaId")
    @Mapping(source = "motivoDoCadastro.id", target = "motivoDoCadastroId")
    CartaoSUSDTO toDto(CartaoSUS cartaoSUS);

    @Mapping(source = "justificativaId", target = "justificativa")
    @Mapping(source = "motivoDoCadastroId", target = "motivoDoCadastro")
    @Mapping(target = "paciente", ignore = true)
    CartaoSUS toEntity(CartaoSUSDTO cartaoSUSDTO);

    default CartaoSUS fromId(Long id) {
        if (id == null) {
            return null;
        }
        CartaoSUS cartaoSUS = new CartaoSUS();
        cartaoSUS.setId(id);
        return cartaoSUS;
    }
}
