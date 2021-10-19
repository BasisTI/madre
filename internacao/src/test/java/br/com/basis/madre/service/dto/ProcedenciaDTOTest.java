package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ProcedenciaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedenciaDTO.class);
        ProcedenciaDTO procedenciaDTO1 = new ProcedenciaDTO();
        procedenciaDTO1.setId(1L);
        ProcedenciaDTO procedenciaDTO2 = new ProcedenciaDTO();
        assertThat(procedenciaDTO1).isNotEqualTo(procedenciaDTO2);
        procedenciaDTO2.setId(procedenciaDTO1.getId());
        assertThat(procedenciaDTO1).isEqualTo(procedenciaDTO2);
        procedenciaDTO2.setId(2L);
        assertThat(procedenciaDTO1).isNotEqualTo(procedenciaDTO2);
        procedenciaDTO1.setId(null);
        assertThat(procedenciaDTO1).isNotEqualTo(procedenciaDTO2);
    }
}
