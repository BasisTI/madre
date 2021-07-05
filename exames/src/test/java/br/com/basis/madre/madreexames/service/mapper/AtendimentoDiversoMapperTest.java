package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AtendimentoDiversoMapperTest {

    private AtendimentoDiversoMapper atendimentoDiversoMapper;

    @BeforeEach
    public void setUp() {
        atendimentoDiversoMapper = new AtendimentoDiversoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(atendimentoDiversoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(atendimentoDiversoMapper.fromId(null)).isNull();
    }
}
