package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class InformacaoTransferenciaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformacaoTransferenciaDTO.class);
        InformacaoTransferenciaDTO informacaoTransferenciaDTO1 = new InformacaoTransferenciaDTO();
        informacaoTransferenciaDTO1.setId(1L);
        InformacaoTransferenciaDTO informacaoTransferenciaDTO2 = new InformacaoTransferenciaDTO();
        assertThat(informacaoTransferenciaDTO1).isNotEqualTo(informacaoTransferenciaDTO2);
        informacaoTransferenciaDTO2.setId(informacaoTransferenciaDTO1.getId());
        assertThat(informacaoTransferenciaDTO1).isEqualTo(informacaoTransferenciaDTO2);
        informacaoTransferenciaDTO2.setId(2L);
        assertThat(informacaoTransferenciaDTO1).isNotEqualTo(informacaoTransferenciaDTO2);
        informacaoTransferenciaDTO1.setId(null);
        assertThat(informacaoTransferenciaDTO1).isNotEqualTo(informacaoTransferenciaDTO2);
    }
}
