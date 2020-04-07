package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TelefoneMapperTest {

    private TelefoneMapper telefoneMapper;

    @BeforeEach
    public void setUp() {
        telefoneMapper = new TelefoneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(telefoneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(telefoneMapper.fromId(null)).isNull();
    }
}
