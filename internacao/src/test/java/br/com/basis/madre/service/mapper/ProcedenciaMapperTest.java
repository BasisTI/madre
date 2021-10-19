package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcedenciaMapperTest {

    private ProcedenciaMapper procedenciaMapper;

    @BeforeEach
    public void setUp() {
        procedenciaMapper = new ProcedenciaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(procedenciaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(procedenciaMapper.fromId(null)).isNull();
    }
}
