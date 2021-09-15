package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MaterialDeExameMapperTest {

    private MaterialDeExameMapper materialDeExameMapper;

    @BeforeEach
    public void setUp() {
        materialDeExameMapper = new MaterialDeExameMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(materialDeExameMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(materialDeExameMapper.fromId(null)).isNull();
    }
}
