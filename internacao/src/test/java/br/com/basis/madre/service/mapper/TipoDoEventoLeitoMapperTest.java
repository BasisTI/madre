package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoDoEventoLeitoMapperTest {

    private TipoDoEventoLeitoMapper tipoDoEventoLeitoMapper;

    @BeforeEach
    public void setUp() {
        tipoDoEventoLeitoMapper = new TipoDoEventoLeitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoDoEventoLeitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoDoEventoLeitoMapper.fromId(null)).isNull();
    }
}
