package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReligiaoMapperTest {

    private ReligiaoMapper religiaoMapper;

    @BeforeEach
    public void setUp() {
        religiaoMapper = new ReligiaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(religiaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(religiaoMapper.fromId(null)).isNull();
    }
}
