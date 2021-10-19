package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ProcedimentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedimentoDTO.class);
        ProcedimentoDTO procedimentoDTO1 = new ProcedimentoDTO();
        procedimentoDTO1.setId(1L);
        ProcedimentoDTO procedimentoDTO2 = new ProcedimentoDTO();
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
        procedimentoDTO2.setId(procedimentoDTO1.getId());
        assertThat(procedimentoDTO1).isEqualTo(procedimentoDTO2);
        procedimentoDTO2.setId(2L);
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
        procedimentoDTO1.setId(null);
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
    }
}
