package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OcupacaoDeCargoMapperTest {

    private OcupacaoDeCargoMapper ocupacaoDeCargoMapper;

    @BeforeEach
    public void setUp() {
        ocupacaoDeCargoMapper = new OcupacaoDeCargoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ocupacaoDeCargoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ocupacaoDeCargoMapper.fromId(null)).isNull();
    }
}
