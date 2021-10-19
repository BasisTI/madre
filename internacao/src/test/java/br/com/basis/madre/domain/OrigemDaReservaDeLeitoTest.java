package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class OrigemDaReservaDeLeitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemDaReservaDeLeito.class);
        OrigemDaReservaDeLeito origemDaReservaDeLeito1 = new OrigemDaReservaDeLeito();
        origemDaReservaDeLeito1.setId(1L);
        OrigemDaReservaDeLeito origemDaReservaDeLeito2 = new OrigemDaReservaDeLeito();
        origemDaReservaDeLeito2.setId(origemDaReservaDeLeito1.getId());
        assertThat(origemDaReservaDeLeito1).isEqualTo(origemDaReservaDeLeito2);
        origemDaReservaDeLeito2.setId(2L);
        assertThat(origemDaReservaDeLeito1).isNotEqualTo(origemDaReservaDeLeito2);
        origemDaReservaDeLeito1.setId(null);
        assertThat(origemDaReservaDeLeito1).isNotEqualTo(origemDaReservaDeLeito2);
    }
}
