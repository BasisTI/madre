package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AmostraDeMaterialMapperTest {

    private AmostraDeMaterialMapper amostraDeMaterialMapper;

    @BeforeEach
    public void setUp() {
        amostraDeMaterialMapper = new AmostraDeMaterialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(amostraDeMaterialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(amostraDeMaterialMapper.fromId(null)).isNull();
    }
}
