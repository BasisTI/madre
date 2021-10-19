package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UnidadeFuncionalMapperTest {

    private UnidadeFuncionalMapper unidadeFuncionalMapper;

    @BeforeEach
    public void setUp() {
        unidadeFuncionalMapper = new UnidadeFuncionalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(unidadeFuncionalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(unidadeFuncionalMapper.fromId(null)).isNull();
    }
}
