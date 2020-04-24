package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OcupacaoMapperTest {

    private OcupacaoMapper ocupacaoMapper;

    @BeforeEach
    public void setUp() {
        ocupacaoMapper = new OcupacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ocupacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ocupacaoMapper.fromId(null)).isNull();
    }
}
