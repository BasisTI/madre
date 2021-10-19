package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConvenioDeSaudeMapperTest {

    private ConvenioDeSaudeMapper convenioDeSaudeMapper;

    @BeforeEach
    public void setUp() {
        convenioDeSaudeMapper = new ConvenioDeSaudeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(convenioDeSaudeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(convenioDeSaudeMapper.fromId(null)).isNull();
    }
}
