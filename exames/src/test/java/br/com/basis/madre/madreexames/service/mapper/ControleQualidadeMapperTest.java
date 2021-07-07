package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ControleQualidadeMapperTest {

    private ControleQualidadeMapper controleQualidadeMapper;

    @BeforeEach
    public void setUp() {
        controleQualidadeMapper = new ControleQualidadeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(controleQualidadeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(controleQualidadeMapper.fromId(null)).isNull();
    }
}
