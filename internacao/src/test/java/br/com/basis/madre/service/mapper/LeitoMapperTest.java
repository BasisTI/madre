package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LeitoMapperTest {

    private LeitoMapper leitoMapper;

    @BeforeEach
    public void setUp() {
        leitoMapper = new LeitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(leitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(leitoMapper.fromId(null)).isNull();
    }
}
