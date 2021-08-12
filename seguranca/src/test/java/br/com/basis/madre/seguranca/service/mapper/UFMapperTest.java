package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UFMapperTest {

    private UFMapper uFMapper;

    @BeforeEach
    public void setUp() {
        uFMapper = new UFMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(uFMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(uFMapper.fromId(null)).isNull();
    }
}
