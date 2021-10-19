package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class PlanoDeSaudeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoDeSaude.class);
        PlanoDeSaude planoDeSaude1 = new PlanoDeSaude();
        planoDeSaude1.setId(1L);
        PlanoDeSaude planoDeSaude2 = new PlanoDeSaude();
        planoDeSaude2.setId(planoDeSaude1.getId());
        assertThat(planoDeSaude1).isEqualTo(planoDeSaude2);
        planoDeSaude2.setId(2L);
        assertThat(planoDeSaude1).isNotEqualTo(planoDeSaude2);
        planoDeSaude1.setId(null);
        assertThat(planoDeSaude1).isNotEqualTo(planoDeSaude2);
    }
}
