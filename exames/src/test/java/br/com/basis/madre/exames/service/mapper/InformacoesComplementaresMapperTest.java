package br.com.basis.madre.exames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InformacoesComplementaresMapperTest {

    private InformacoesComplementaresMapper informacoesComplementaresMapper;

    @BeforeEach
    public void setUp() {
        informacoesComplementaresMapper = new InformacoesComplementaresMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(informacoesComplementaresMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(informacoesComplementaresMapper.fromId(null)).isNull();
    }
}
