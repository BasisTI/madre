package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EstadoCivilMapperTest {

    private EstadoCivilMapper estadoCivilMapper;

    @BeforeEach
    public void setUp() {
        estadoCivilMapper = new EstadoCivilMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(estadoCivilMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(estadoCivilMapper.fromId(null)).isNull();
    }
}
