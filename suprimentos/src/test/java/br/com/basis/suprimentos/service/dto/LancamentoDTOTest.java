package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class LancamentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LancamentoDTO.class);
        LancamentoDTO lancamentoDTO1 = new LancamentoDTO();
        lancamentoDTO1.setId(1L);
        LancamentoDTO lancamentoDTO2 = new LancamentoDTO();
        assertThat(lancamentoDTO1).isNotEqualTo(lancamentoDTO2);
        lancamentoDTO2.setId(lancamentoDTO1.getId());
        assertThat(lancamentoDTO1).isEqualTo(lancamentoDTO2);
        lancamentoDTO2.setId(2L);
        assertThat(lancamentoDTO1).isNotEqualTo(lancamentoDTO2);
        lancamentoDTO1.setId(null);
        assertThat(lancamentoDTO1).isNotEqualTo(lancamentoDTO2);
    }
}
