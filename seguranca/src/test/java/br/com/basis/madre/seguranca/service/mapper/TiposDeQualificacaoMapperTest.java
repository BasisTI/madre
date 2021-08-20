package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TiposDeQualificacaoMapperTest {

    private TiposDeQualificacaoMapper tiposDeQualificacaoMapper;

    @BeforeEach
    public void setUp() {
        tiposDeQualificacaoMapper = new TiposDeQualificacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tiposDeQualificacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tiposDeQualificacaoMapper.fromId(null)).isNull();
    }
}
