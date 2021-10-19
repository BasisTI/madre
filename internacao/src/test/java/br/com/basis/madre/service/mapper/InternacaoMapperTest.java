package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InternacaoMapperTest {

    private InternacaoMapper internacaoMapper;

    @BeforeEach
    public void setUp() {
        internacaoMapper = new InternacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(internacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(internacaoMapper.fromId(null)).isNull();
    }
}
