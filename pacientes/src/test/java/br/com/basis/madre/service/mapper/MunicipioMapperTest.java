package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MunicipioMapperTest {

    private MunicipioMapper municipioMapper;

    @BeforeEach
    public void setUp() {
        municipioMapper = new MunicipioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(municipioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(municipioMapper.fromId(null)).isNull();
    }
}
