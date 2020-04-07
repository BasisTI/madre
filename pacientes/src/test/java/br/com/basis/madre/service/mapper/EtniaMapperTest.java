package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EtniaMapperTest {

    private EtniaMapper etniaMapper;

    @BeforeEach
    public void setUp() {
        etniaMapper = new EtniaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(etniaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(etniaMapper.fromId(null)).isNull();
    }
}
