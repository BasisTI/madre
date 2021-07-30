package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioMapperTest {

    private UsuarioMapper usuarioMapper;

    @BeforeEach
    public void setUp() {
        usuarioMapper = new UsuarioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(usuarioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(usuarioMapper.fromId(null)).isNull();
    }
}
