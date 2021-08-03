package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjetoDePesquisaMapperTest {

    private ProjetoDePesquisaMapper projetoDePesquisaMapper;

    @BeforeEach
    public void setUp() {
        projetoDePesquisaMapper = new ProjetoDePesquisaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(projetoDePesquisaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(projetoDePesquisaMapper.fromId(null)).isNull();
    }
}
