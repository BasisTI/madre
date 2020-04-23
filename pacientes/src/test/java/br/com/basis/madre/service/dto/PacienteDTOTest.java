package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class PacienteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacienteDTO.class);
        PacienteDTO pacienteDTO1 = new PacienteDTO();
        pacienteDTO1.setId(1L);
        PacienteDTO pacienteDTO2 = new PacienteDTO();
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO2.setId(pacienteDTO1.getId());
        assertThat(pacienteDTO1).isEqualTo(pacienteDTO2);
        pacienteDTO2.setId(2L);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO1.setId(null);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
    }
}
