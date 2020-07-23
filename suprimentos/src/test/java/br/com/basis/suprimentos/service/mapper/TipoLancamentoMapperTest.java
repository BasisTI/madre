package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoLancamentoMapperTest {

    private TipoLancamentoMapper tipoLancamentoMapper;

    @BeforeEach
    public void setUp() {
        tipoLancamentoMapper = new TipoLancamentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoLancamentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoLancamentoMapper.fromId(null)).isNull();
    }
}
