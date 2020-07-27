package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransacaoMapperTest {

    private TransacaoMapper transacaoMapper;

    @BeforeEach
    public void setUp() {
        transacaoMapper = new TransacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transacaoMapper.fromId(null)).isNull();
    }
}
