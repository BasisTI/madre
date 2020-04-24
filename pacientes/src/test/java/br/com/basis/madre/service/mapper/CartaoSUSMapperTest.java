package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CartaoSUSMapperTest {

    private CartaoSUSMapper cartaoSUSMapper;

    @BeforeEach
    public void setUp() {
        cartaoSUSMapper = new CartaoSUSMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cartaoSUSMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cartaoSUSMapper.fromId(null)).isNull();
    }
}
