package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrigemDaReservaDeLeitoMapperTest {

    private OrigemDaReservaDeLeitoMapper origemDaReservaDeLeitoMapper;

    @BeforeEach
    public void setUp() {
        origemDaReservaDeLeitoMapper = new OrigemDaReservaDeLeitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(origemDaReservaDeLeitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(origemDaReservaDeLeitoMapper.fromId(null)).isNull();
    }
}
