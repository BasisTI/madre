package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SolicitacaoDeInternacaoMapperTest {

    private SolicitacaoDeInternacaoMapper solicitacaoDeInternacaoMapper;

    @BeforeEach
    public void setUp() {
        solicitacaoDeInternacaoMapper = new SolicitacaoDeInternacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(solicitacaoDeInternacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(solicitacaoDeInternacaoMapper.fromId(null)).isNull();
    }
}
