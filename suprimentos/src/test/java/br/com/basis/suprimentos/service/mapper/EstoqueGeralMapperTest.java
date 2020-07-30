package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EstoqueGeralMapperTest {

    private EstoqueGeralMapper estoqueGeralMapper;

    @BeforeEach
    public void setUp() {
        estoqueGeralMapper = new EstoqueGeralMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(estoqueGeralMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(estoqueGeralMapper.fromId(null)).isNull();
    }
}
