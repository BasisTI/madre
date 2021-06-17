package br.com.basis.madre.exames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExameMapperTest {

    private ExameMapper exameMapper;

    @BeforeEach
    public void setUp() {
        exameMapper = new ExameMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(exameMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(exameMapper.fromId(null)).isNull();
    }
}
