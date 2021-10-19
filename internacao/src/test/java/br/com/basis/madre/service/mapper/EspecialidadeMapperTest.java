package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EspecialidadeMapperTest {

    private EspecialidadeMapper especialidadeMapper;

    @BeforeEach
    public void setUp() {
        especialidadeMapper = new EspecialidadeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(especialidadeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(especialidadeMapper.fromId(null)).isNull();
    }
}
