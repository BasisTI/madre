package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrigemDaInternacaoMapperTest {

    private OrigemDaInternacaoMapper origemDaInternacaoMapper;

    @BeforeEach
    public void setUp() {
        origemDaInternacaoMapper = new OrigemDaInternacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(origemDaInternacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(origemDaInternacaoMapper.fromId(null)).isNull();
    }
}
