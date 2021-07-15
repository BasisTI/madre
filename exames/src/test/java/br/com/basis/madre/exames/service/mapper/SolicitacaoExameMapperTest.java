package br.com.basis.madre.exames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SolicitacaoExameMapperTest {

    private SolicitacaoExameMapper solicitacaoExameMapper;

    @BeforeEach
    public void setUp() {
        solicitacaoExameMapper = new SolicitacaoExameMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(solicitacaoExameMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(solicitacaoExameMapper.fromId(null)).isNull();
    }
}
