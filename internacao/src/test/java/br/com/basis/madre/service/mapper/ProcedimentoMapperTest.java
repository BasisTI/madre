package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcedimentoMapperTest {

    private ProcedimentoMapper procedimentoMapper;

    @BeforeEach
    public void setUp() {
        procedimentoMapper = new ProcedimentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(procedimentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(procedimentoMapper.fromId(null)).isNull();
    }
}
