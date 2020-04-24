package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NacionalidadeMapperTest {

    private NacionalidadeMapper nacionalidadeMapper;

    @BeforeEach
    public void setUp() {
        nacionalidadeMapper = new NacionalidadeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(nacionalidadeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(nacionalidadeMapper.fromId(null)).isNull();
    }
}
