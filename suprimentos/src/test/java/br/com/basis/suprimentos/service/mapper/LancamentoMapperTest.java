package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LancamentoMapperTest {

    private LancamentoMapper lancamentoMapper;

    @BeforeEach
    public void setUp() {
        lancamentoMapper = new LancamentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(lancamentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lancamentoMapper.fromId(null)).isNull();
    }
}
