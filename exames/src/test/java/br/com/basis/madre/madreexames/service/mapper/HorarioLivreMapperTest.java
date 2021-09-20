package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HorarioLivreMapperTest {

    private HorarioLivreMapper horarioLivreMapper;

    @BeforeEach
    public void setUp() {
        horarioLivreMapper = new HorarioLivreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(horarioLivreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(horarioLivreMapper.fromId(null)).isNull();
    }
}
