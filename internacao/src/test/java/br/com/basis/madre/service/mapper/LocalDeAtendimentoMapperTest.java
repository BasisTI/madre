package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LocalDeAtendimentoMapperTest {

    private LocalDeAtendimentoMapper localDeAtendimentoMapper;

    @BeforeEach
    public void setUp() {
        localDeAtendimentoMapper = new LocalDeAtendimentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(localDeAtendimentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(localDeAtendimentoMapper.fromId(null)).isNull();
    }
}
