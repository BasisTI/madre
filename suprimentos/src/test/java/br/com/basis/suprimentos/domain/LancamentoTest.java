package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class LancamentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lancamento.class);
        Lancamento lancamento1 = new Lancamento();
        lancamento1.setId(1L);
        Lancamento lancamento2 = new Lancamento();
        lancamento2.setId(lancamento1.getId());
        assertThat(lancamento1).isEqualTo(lancamento2);
        lancamento2.setId(2L);
        assertThat(lancamento1).isNotEqualTo(lancamento2);
        lancamento1.setId(null);
        assertThat(lancamento1).isNotEqualTo(lancamento2);
    }
}
