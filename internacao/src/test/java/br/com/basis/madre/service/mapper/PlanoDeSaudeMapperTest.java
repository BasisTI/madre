package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanoDeSaudeMapperTest {

    private PlanoDeSaudeMapper planoDeSaudeMapper;

    @BeforeEach
    public void setUp() {
        planoDeSaudeMapper = new PlanoDeSaudeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planoDeSaudeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planoDeSaudeMapper.fromId(null)).isNull();
    }
}
