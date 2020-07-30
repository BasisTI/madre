package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class TipoLancamentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoLancamento.class);
        TipoLancamento tipoLancamento1 = new TipoLancamento();
        tipoLancamento1.setId(1L);
        TipoLancamento tipoLancamento2 = new TipoLancamento();
        tipoLancamento2.setId(tipoLancamento1.getId());
        assertThat(tipoLancamento1).isEqualTo(tipoLancamento2);
        tipoLancamento2.setId(2L);
        assertThat(tipoLancamento1).isNotEqualTo(tipoLancamento2);
        tipoLancamento1.setId(null);
        assertThat(tipoLancamento1).isNotEqualTo(tipoLancamento2);
    }
}
