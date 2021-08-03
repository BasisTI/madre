package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoAmostraMapperTest {

    private TipoAmostraMapper tipoAmostraMapper;

    @BeforeEach
    public void setUp() {
        tipoAmostraMapper = new TipoAmostraMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoAmostraMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoAmostraMapper.fromId(null)).isNull();
    }
}
