package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GraduacaoMapperTest {

    private GraduacaoMapper graduacaoMapper;

    @BeforeEach
    public void setUp() {
        graduacaoMapper = new GraduacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(graduacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(graduacaoMapper.fromId(null)).isNull();
    }
}
