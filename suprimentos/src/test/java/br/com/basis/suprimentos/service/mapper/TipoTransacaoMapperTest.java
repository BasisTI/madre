package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoTransacaoMapperTest {

    private TipoTransacaoMapper tipoTransacaoMapper;

    @BeforeEach
    public void setUp() {
        tipoTransacaoMapper = new TipoTransacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoTransacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoTransacaoMapper.fromId(null)).isNull();
    }
}
