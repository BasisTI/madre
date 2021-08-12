package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RamalMapperTest {

    private RamalMapper ramalMapper;

    @BeforeEach
    public void setUp() {
        ramalMapper = new RamalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ramalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ramalMapper.fromId(null)).isNull();
    }
}
