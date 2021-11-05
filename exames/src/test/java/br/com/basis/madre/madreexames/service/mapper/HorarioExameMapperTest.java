package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HorarioExameMapperTest {

    private HorarioExameMapper horarioExameMapper;

    @BeforeEach
    public void setUp() {
        horarioExameMapper = new HorarioExameMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(horarioExameMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(horarioExameMapper.fromId(null)).isNull();
    }
}
