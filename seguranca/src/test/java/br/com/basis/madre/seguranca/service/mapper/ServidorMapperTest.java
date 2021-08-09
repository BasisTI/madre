package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServidorMapperTest {

    private ServidorMapper servidorMapper;

    @BeforeEach
    public void setUp() {
        servidorMapper = new ServidorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(servidorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(servidorMapper.fromId(null)).isNull();
    }
}
