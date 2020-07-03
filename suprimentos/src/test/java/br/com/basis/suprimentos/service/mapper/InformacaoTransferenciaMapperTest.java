package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InformacaoTransferenciaMapperTest {

    private InformacaoTransferenciaMapper informacaoTransferenciaMapper;

    @BeforeEach
    public void setUp() {
        informacaoTransferenciaMapper = new InformacaoTransferenciaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(informacaoTransferenciaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(informacaoTransferenciaMapper.fromId(null)).isNull();
    }
}
