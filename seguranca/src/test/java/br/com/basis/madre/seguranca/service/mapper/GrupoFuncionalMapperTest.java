package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GrupoFuncionalMapperTest {

    private GrupoFuncionalMapper grupoFuncionalMapper;

    @BeforeEach
    public void setUp() {
        grupoFuncionalMapper = new GrupoFuncionalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(grupoFuncionalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(grupoFuncionalMapper.fromId(null)).isNull();
    }
}
