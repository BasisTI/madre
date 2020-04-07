package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MotivoDoCadastroMapperTest {

    private MotivoDoCadastroMapper motivoDoCadastroMapper;

    @BeforeEach
    public void setUp() {
        motivoDoCadastroMapper = new MotivoDoCadastroMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(motivoDoCadastroMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(motivoDoCadastroMapper.fromId(null)).isNull();
    }
}
