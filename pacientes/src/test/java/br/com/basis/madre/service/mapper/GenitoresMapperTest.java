package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GenitoresMapperTest {

    private GenitoresMapper genitoresMapper;

    @BeforeEach
    public void setUp() {
        genitoresMapper = new GenitoresMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(genitoresMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(genitoresMapper.fromId(null)).isNull();
    }
}
