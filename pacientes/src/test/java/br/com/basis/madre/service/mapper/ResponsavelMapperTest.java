package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ResponsavelMapperTest {

    private ResponsavelMapper responsavelMapper;

    @BeforeEach
    public void setUp() {
        responsavelMapper = new ResponsavelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(responsavelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(responsavelMapper.fromId(null)).isNull();
    }
}
