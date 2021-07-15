package br.com.basis.madre.exames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecomendacaoMapperTest {

    private RecomendacaoMapper recomendacaoMapper;

    @BeforeEach
    public void setUp() {
        recomendacaoMapper = new RecomendacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recomendacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recomendacaoMapper.fromId(null)).isNull();
    }
}
