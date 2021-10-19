package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class TipoDaReservaDeLeitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDaReservaDeLeito.class);
        TipoDaReservaDeLeito tipoDaReservaDeLeito1 = new TipoDaReservaDeLeito();
        tipoDaReservaDeLeito1.setId(1L);
        TipoDaReservaDeLeito tipoDaReservaDeLeito2 = new TipoDaReservaDeLeito();
        tipoDaReservaDeLeito2.setId(tipoDaReservaDeLeito1.getId());
        assertThat(tipoDaReservaDeLeito1).isEqualTo(tipoDaReservaDeLeito2);
        tipoDaReservaDeLeito2.setId(2L);
        assertThat(tipoDaReservaDeLeito1).isNotEqualTo(tipoDaReservaDeLeito2);
        tipoDaReservaDeLeito1.setId(null);
        assertThat(tipoDaReservaDeLeito1).isNotEqualTo(tipoDaReservaDeLeito2);
    }
}
