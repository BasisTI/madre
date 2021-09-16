package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoDeMarcacaoMapperTest {

    private TipoDeMarcacaoMapper tipoDeMarcacaoMapper;

    @BeforeEach
    public void setUp() {
        tipoDeMarcacaoMapper = new TipoDeMarcacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoDeMarcacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoDeMarcacaoMapper.fromId(null)).isNull();
    }
}
