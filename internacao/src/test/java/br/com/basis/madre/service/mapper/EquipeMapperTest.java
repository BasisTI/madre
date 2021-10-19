package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EquipeMapperTest {

    private EquipeMapper equipeMapper;

    @BeforeEach
    public void setUp() {
        equipeMapper = new EquipeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(equipeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(equipeMapper.fromId(null)).isNull();
    }
}
