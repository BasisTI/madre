package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VinculoMapperTest {

    private VinculoMapper vinculoMapper;

    @BeforeEach
    public void setUp() {
        vinculoMapper = new VinculoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(vinculoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vinculoMapper.fromId(null)).isNull();
    }
}
