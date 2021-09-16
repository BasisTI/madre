package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AnticoagulanteMapperTest {

    private AnticoagulanteMapper anticoagulanteMapper;

    @BeforeEach
    public void setUp() {
        anticoagulanteMapper = new AnticoagulanteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(anticoagulanteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(anticoagulanteMapper.fromId(null)).isNull();
    }
}
