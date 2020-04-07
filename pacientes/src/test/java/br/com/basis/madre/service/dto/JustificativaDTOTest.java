package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class JustificativaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JustificativaDTO.class);
        JustificativaDTO justificativaDTO1 = new JustificativaDTO();
        justificativaDTO1.setId(1L);
        JustificativaDTO justificativaDTO2 = new JustificativaDTO();
        assertThat(justificativaDTO1).isNotEqualTo(justificativaDTO2);
        justificativaDTO2.setId(justificativaDTO1.getId());
        assertThat(justificativaDTO1).isEqualTo(justificativaDTO2);
        justificativaDTO2.setId(2L);
        assertThat(justificativaDTO1).isNotEqualTo(justificativaDTO2);
        justificativaDTO1.setId(null);
        assertThat(justificativaDTO1).isNotEqualTo(justificativaDTO2);
    }
}
