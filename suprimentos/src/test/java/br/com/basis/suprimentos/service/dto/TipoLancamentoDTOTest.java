package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class TipoLancamentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoLancamentoDTO.class);
        TipoLancamentoDTO tipoLancamentoDTO1 = new TipoLancamentoDTO();
        tipoLancamentoDTO1.setId(1L);
        TipoLancamentoDTO tipoLancamentoDTO2 = new TipoLancamentoDTO();
        assertThat(tipoLancamentoDTO1).isNotEqualTo(tipoLancamentoDTO2);
        tipoLancamentoDTO2.setId(tipoLancamentoDTO1.getId());
        assertThat(tipoLancamentoDTO1).isEqualTo(tipoLancamentoDTO2);
        tipoLancamentoDTO2.setId(2L);
        assertThat(tipoLancamentoDTO1).isNotEqualTo(tipoLancamentoDTO2);
        tipoLancamentoDTO1.setId(null);
        assertThat(tipoLancamentoDTO1).isNotEqualTo(tipoLancamentoDTO2);
    }
}
