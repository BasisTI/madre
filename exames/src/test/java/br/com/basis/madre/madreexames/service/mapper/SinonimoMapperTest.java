package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SinonimoMapperTest {

    private SinonimoMapper sinonimoMapper;

    @BeforeEach
    public void setUp() {
        sinonimoMapper = new SinonimoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sinonimoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sinonimoMapper.fromId(null)).isNull();
    }
}
