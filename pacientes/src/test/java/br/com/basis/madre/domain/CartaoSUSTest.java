package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CartaoSUSTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartaoSUS.class);
        CartaoSUS cartaoSUS1 = new CartaoSUS();
        cartaoSUS1.setId(1L);
        CartaoSUS cartaoSUS2 = new CartaoSUS();
        cartaoSUS2.setId(cartaoSUS1.getId());
        assertThat(cartaoSUS1).isEqualTo(cartaoSUS2);
        cartaoSUS2.setId(2L);
        assertThat(cartaoSUS1).isNotEqualTo(cartaoSUS2);
        cartaoSUS1.setId(null);
        assertThat(cartaoSUS1).isNotEqualTo(cartaoSUS2);
    }
}
