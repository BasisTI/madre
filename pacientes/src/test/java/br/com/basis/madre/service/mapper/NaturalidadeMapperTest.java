package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NaturalidadeMapperTest {

    private NaturalidadeMapper naturalidadeMapper;

    @BeforeEach
    public void setUp() {
        naturalidadeMapper = new NaturalidadeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(naturalidadeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(naturalidadeMapper.fromId(null)).isNull();
    }
}
