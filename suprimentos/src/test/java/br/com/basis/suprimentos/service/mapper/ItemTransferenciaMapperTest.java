package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemTransferenciaMapperTest {

    private ItemTransferenciaMapper itemTransferenciaMapper;

    @BeforeEach
    public void setUp() {
        itemTransferenciaMapper = new ItemTransferenciaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(itemTransferenciaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(itemTransferenciaMapper.fromId(null)).isNull();
    }
}
