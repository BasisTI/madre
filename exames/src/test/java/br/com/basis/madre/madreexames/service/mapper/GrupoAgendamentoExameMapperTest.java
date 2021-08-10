package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GrupoAgendamentoExameMapperTest {

    private GrupoAgendamentoExameMapper grupoAgendamentoExameMapper;

    @BeforeEach
    public void setUp() {
        grupoAgendamentoExameMapper = new GrupoAgendamentoExameMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(grupoAgendamentoExameMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(grupoAgendamentoExameMapper.fromId(null)).isNull();
    }
}
