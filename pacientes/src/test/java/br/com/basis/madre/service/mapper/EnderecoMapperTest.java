package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnderecoMapperTest {

    private EnderecoMapper enderecoMapper;

    @BeforeEach
    public void setUp() {
        enderecoMapper = new EnderecoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enderecoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enderecoMapper.fromId(null)).isNull();
    }
}
