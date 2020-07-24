package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class EstoqueGeralTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstoqueGeral.class);
        EstoqueGeral estoqueGeral1 = new EstoqueGeral();
        estoqueGeral1.setId(1L);
        EstoqueGeral estoqueGeral2 = new EstoqueGeral();
        estoqueGeral2.setId(estoqueGeral1.getId());
        assertThat(estoqueGeral1).isEqualTo(estoqueGeral2);
        estoqueGeral2.setId(2L);
        assertThat(estoqueGeral1).isNotEqualTo(estoqueGeral2);
        estoqueGeral1.setId(null);
        assertThat(estoqueGeral1).isNotEqualTo(estoqueGeral2);
    }
}
