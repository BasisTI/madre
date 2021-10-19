package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoDaReservaDeLeitoMapperTest {

    private TipoDaReservaDeLeitoMapper tipoDaReservaDeLeitoMapper;

    @BeforeEach
    public void setUp() {
        tipoDaReservaDeLeitoMapper = new TipoDaReservaDeLeitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoDaReservaDeLeitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoDaReservaDeLeitoMapper.fromId(null)).isNull();
    }
}
