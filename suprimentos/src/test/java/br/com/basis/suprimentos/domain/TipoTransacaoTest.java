package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class TipoTransacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoTransacao.class);
        TipoTransacao tipoTransacao1 = new TipoTransacao();
        tipoTransacao1.setId(1L);
        TipoTransacao tipoTransacao2 = new TipoTransacao();
        tipoTransacao2.setId(tipoTransacao1.getId());
        assertThat(tipoTransacao1).isEqualTo(tipoTransacao2);
        tipoTransacao2.setId(2L);
        assertThat(tipoTransacao1).isNotEqualTo(tipoTransacao2);
        tipoTransacao1.setId(null);
        assertThat(tipoTransacao1).isNotEqualTo(tipoTransacao2);
    }
}
