package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GrauDeParentescoMapperTest {

    private GrauDeParentescoMapper grauDeParentescoMapper;

    @BeforeEach
    public void setUp() {
        grauDeParentescoMapper = new GrauDeParentescoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(grauDeParentescoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(grauDeParentescoMapper.fromId(null)).isNull();
    }
}
