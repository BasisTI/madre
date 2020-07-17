package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SituacaoRequisicaoMapperTest {

    private SituacaoRequisicaoMapper situacaoRequisicaoMapper;

    @BeforeEach
    public void setUp() {
        situacaoRequisicaoMapper = new SituacaoRequisicaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(situacaoRequisicaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(situacaoRequisicaoMapper.fromId(null)).isNull();
    }
}
