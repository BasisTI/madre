package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CRMMapperTest {

    private CRMMapper cRMMapper;

    @BeforeEach
    public void setUp() {
        cRMMapper = new CRMMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cRMMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cRMMapper.fromId(null)).isNull();
    }
}
