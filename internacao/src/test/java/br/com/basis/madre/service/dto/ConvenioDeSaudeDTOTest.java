package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ConvenioDeSaudeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConvenioDeSaudeDTO.class);
        ConvenioDeSaudeDTO convenioDeSaudeDTO1 = new ConvenioDeSaudeDTO();
        convenioDeSaudeDTO1.setId(1L);
        ConvenioDeSaudeDTO convenioDeSaudeDTO2 = new ConvenioDeSaudeDTO();
        assertThat(convenioDeSaudeDTO1).isNotEqualTo(convenioDeSaudeDTO2);
        convenioDeSaudeDTO2.setId(convenioDeSaudeDTO1.getId());
        assertThat(convenioDeSaudeDTO1).isEqualTo(convenioDeSaudeDTO2);
        convenioDeSaudeDTO2.setId(2L);
        assertThat(convenioDeSaudeDTO1).isNotEqualTo(convenioDeSaudeDTO2);
        convenioDeSaudeDTO1.setId(null);
        assertThat(convenioDeSaudeDTO1).isNotEqualTo(convenioDeSaudeDTO2);
    }
}
