package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HorarioAgendadoMapperTest {

    private HorarioAgendadoMapper horarioAgendadoMapper;

    @BeforeEach
    public void setUp() {
        horarioAgendadoMapper = new HorarioAgendadoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(horarioAgendadoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(horarioAgendadoMapper.fromId(null)).isNull();
    }
}
