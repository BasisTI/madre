package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class InformacaoTransferenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformacaoTransferencia.class);
        InformacaoTransferencia informacaoTransferencia1 = new InformacaoTransferencia();
        informacaoTransferencia1.setId(1L);
        InformacaoTransferencia informacaoTransferencia2 = new InformacaoTransferencia();
        informacaoTransferencia2.setId(informacaoTransferencia1.getId());
        assertThat(informacaoTransferencia1).isEqualTo(informacaoTransferencia2);
        informacaoTransferencia2.setId(2L);
        assertThat(informacaoTransferencia1).isNotEqualTo(informacaoTransferencia2);
        informacaoTransferencia1.setId(null);
        assertThat(informacaoTransferencia1).isNotEqualTo(informacaoTransferencia2);
    }
}
