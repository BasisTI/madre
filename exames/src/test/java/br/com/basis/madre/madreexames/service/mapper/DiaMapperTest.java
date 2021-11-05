package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DiaMapperTest {

    private DiaMapper diaMapper;

    @BeforeEach
    public void setUp() {
        diaMapper = new DiaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(diaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(diaMapper.fromId(null)).isNull();
    }
}
