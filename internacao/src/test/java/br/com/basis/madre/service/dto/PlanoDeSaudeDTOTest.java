package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class PlanoDeSaudeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoDeSaudeDTO.class);
        PlanoDeSaudeDTO planoDeSaudeDTO1 = new PlanoDeSaudeDTO();
        planoDeSaudeDTO1.setId(1L);
        PlanoDeSaudeDTO planoDeSaudeDTO2 = new PlanoDeSaudeDTO();
        assertThat(planoDeSaudeDTO1).isNotEqualTo(planoDeSaudeDTO2);
        planoDeSaudeDTO2.setId(planoDeSaudeDTO1.getId());
        assertThat(planoDeSaudeDTO1).isEqualTo(planoDeSaudeDTO2);
        planoDeSaudeDTO2.setId(2L);
        assertThat(planoDeSaudeDTO1).isNotEqualTo(planoDeSaudeDTO2);
        planoDeSaudeDTO1.setId(null);
        assertThat(planoDeSaudeDTO1).isNotEqualTo(planoDeSaudeDTO2);
    }
}
