package br.com.basis.madre.exames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LaboratorioExternoMapperTest {

    private LaboratorioExternoMapper laboratorioExternoMapper;

    @BeforeEach
    public void setUp() {
        laboratorioExternoMapper = new LaboratorioExternoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(laboratorioExternoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(laboratorioExternoMapper.fromId(null)).isNull();
    }
}
