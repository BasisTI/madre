package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InstituicaoMapperTest {

    private InstituicaoMapper instituicaoMapper;

    @BeforeEach
    public void setUp() {
        instituicaoMapper = new InstituicaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(instituicaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(instituicaoMapper.fromId(null)).isNull();
    }
}
