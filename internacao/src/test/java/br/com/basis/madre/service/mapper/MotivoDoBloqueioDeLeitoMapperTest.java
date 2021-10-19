package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MotivoDoBloqueioDeLeitoMapperTest {

    private MotivoDoBloqueioDeLeitoMapper motivoDoBloqueioDeLeitoMapper;

    @BeforeEach
    public void setUp() {
        motivoDoBloqueioDeLeitoMapper = new MotivoDoBloqueioDeLeitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(motivoDoBloqueioDeLeitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(motivoDoBloqueioDeLeitoMapper.fromId(null)).isNull();
    }
}
