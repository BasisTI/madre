package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SituacaoDeLeitoMapperTest {

    private SituacaoDeLeitoMapper situacaoDeLeitoMapper;

    @BeforeEach
    public void setUp() {
        situacaoDeLeitoMapper = new SituacaoDeLeitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(situacaoDeLeitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(situacaoDeLeitoMapper.fromId(null)).isNull();
    }
}
