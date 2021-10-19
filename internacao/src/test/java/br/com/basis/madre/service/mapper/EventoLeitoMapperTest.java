package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EventoLeitoMapperTest {

    private EventoLeitoMapper eventoLeitoMapper;

    @BeforeEach
    public void setUp() {
        eventoLeitoMapper = new EventoLeitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eventoLeitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventoLeitoMapper.fromId(null)).isNull();
    }
}
