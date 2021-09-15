package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipienteMapperTest {

    private RecipienteMapper recipienteMapper;

    @BeforeEach
    public void setUp() {
        recipienteMapper = new RecipienteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recipienteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recipienteMapper.fromId(null)).isNull();
    }
}
