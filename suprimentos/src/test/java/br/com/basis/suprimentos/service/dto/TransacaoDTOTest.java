package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class TransacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransacaoDTO.class);
        TransacaoDTO transacaoDTO1 = new TransacaoDTO();
        transacaoDTO1.setId(1L);
        TransacaoDTO transacaoDTO2 = new TransacaoDTO();
        assertThat(transacaoDTO1).isNotEqualTo(transacaoDTO2);
        transacaoDTO2.setId(transacaoDTO1.getId());
        assertThat(transacaoDTO1).isEqualTo(transacaoDTO2);
        transacaoDTO2.setId(2L);
        assertThat(transacaoDTO1).isNotEqualTo(transacaoDTO2);
        transacaoDTO1.setId(null);
        assertThat(transacaoDTO1).isNotEqualTo(transacaoDTO2);
    }
}
