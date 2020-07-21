package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class TipoTransacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoTransacaoDTO.class);
        TipoTransacaoDTO tipoTransacaoDTO1 = new TipoTransacaoDTO();
        tipoTransacaoDTO1.setId(1L);
        TipoTransacaoDTO tipoTransacaoDTO2 = new TipoTransacaoDTO();
        assertThat(tipoTransacaoDTO1).isNotEqualTo(tipoTransacaoDTO2);
        tipoTransacaoDTO2.setId(tipoTransacaoDTO1.getId());
        assertThat(tipoTransacaoDTO1).isEqualTo(tipoTransacaoDTO2);
        tipoTransacaoDTO2.setId(2L);
        assertThat(tipoTransacaoDTO1).isNotEqualTo(tipoTransacaoDTO2);
        tipoTransacaoDTO1.setId(null);
        assertThat(tipoTransacaoDTO1).isNotEqualTo(tipoTransacaoDTO2);
    }
}
