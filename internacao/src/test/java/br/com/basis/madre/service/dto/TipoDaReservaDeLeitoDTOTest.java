package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class TipoDaReservaDeLeitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDaReservaDeLeitoDTO.class);
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO1 = new TipoDaReservaDeLeitoDTO();
        tipoDaReservaDeLeitoDTO1.setId(1L);
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO2 = new TipoDaReservaDeLeitoDTO();
        assertThat(tipoDaReservaDeLeitoDTO1).isNotEqualTo(tipoDaReservaDeLeitoDTO2);
        tipoDaReservaDeLeitoDTO2.setId(tipoDaReservaDeLeitoDTO1.getId());
        assertThat(tipoDaReservaDeLeitoDTO1).isEqualTo(tipoDaReservaDeLeitoDTO2);
        tipoDaReservaDeLeitoDTO2.setId(2L);
        assertThat(tipoDaReservaDeLeitoDTO1).isNotEqualTo(tipoDaReservaDeLeitoDTO2);
        tipoDaReservaDeLeitoDTO1.setId(null);
        assertThat(tipoDaReservaDeLeitoDTO1).isNotEqualTo(tipoDaReservaDeLeitoDTO2);
    }
}
