package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JustificativaMapperTest {

    private JustificativaMapper justificativaMapper;

    @BeforeEach
    public void setUp() {
        justificativaMapper = new JustificativaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(justificativaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(justificativaMapper.fromId(null)).isNull();
    }
}
