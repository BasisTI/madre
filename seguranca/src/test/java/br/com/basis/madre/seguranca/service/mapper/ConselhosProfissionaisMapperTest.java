package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConselhosProfissionaisMapperTest {

    private ConselhosProfissionaisMapper conselhosProfissionaisMapper;

    @BeforeEach
    public void setUp() {
        conselhosProfissionaisMapper = new ConselhosProfissionaisMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(conselhosProfissionaisMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(conselhosProfissionaisMapper.fromId(null)).isNull();
    }
}
