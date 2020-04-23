package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CartaoSUSDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartaoSUSDTO.class);
        CartaoSUSDTO cartaoSUSDTO1 = new CartaoSUSDTO();
        cartaoSUSDTO1.setId(1L);
        CartaoSUSDTO cartaoSUSDTO2 = new CartaoSUSDTO();
        assertThat(cartaoSUSDTO1).isNotEqualTo(cartaoSUSDTO2);
        cartaoSUSDTO2.setId(cartaoSUSDTO1.getId());
        assertThat(cartaoSUSDTO1).isEqualTo(cartaoSUSDTO2);
        cartaoSUSDTO2.setId(2L);
        assertThat(cartaoSUSDTO1).isNotEqualTo(cartaoSUSDTO2);
        cartaoSUSDTO1.setId(null);
        assertThat(cartaoSUSDTO1).isNotEqualTo(cartaoSUSDTO2);
    }
}
