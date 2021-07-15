package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CadaverMapperTest {

    private CadaverMapper cadaverMapper;

    @BeforeEach
    public void setUp() {
        cadaverMapper = new CadaverMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cadaverMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cadaverMapper.fromId(null)).isNull();
    }
}
