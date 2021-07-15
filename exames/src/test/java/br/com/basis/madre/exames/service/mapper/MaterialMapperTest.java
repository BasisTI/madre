package br.com.basis.madre.exames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MaterialMapperTest {

    private MaterialMapper materialMapper;

    @BeforeEach
    public void setUp() {
        materialMapper = new MaterialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(materialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(materialMapper.fromId(null)).isNull();
    }
}
