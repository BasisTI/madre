package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class OrigemDaReservaDeLeitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemDaReservaDeLeitoDTO.class);
        OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO1 = new OrigemDaReservaDeLeitoDTO();
        origemDaReservaDeLeitoDTO1.setId(1L);
        OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO2 = new OrigemDaReservaDeLeitoDTO();
        assertThat(origemDaReservaDeLeitoDTO1).isNotEqualTo(origemDaReservaDeLeitoDTO2);
        origemDaReservaDeLeitoDTO2.setId(origemDaReservaDeLeitoDTO1.getId());
        assertThat(origemDaReservaDeLeitoDTO1).isEqualTo(origemDaReservaDeLeitoDTO2);
        origemDaReservaDeLeitoDTO2.setId(2L);
        assertThat(origemDaReservaDeLeitoDTO1).isNotEqualTo(origemDaReservaDeLeitoDTO2);
        origemDaReservaDeLeitoDTO1.setId(null);
        assertThat(origemDaReservaDeLeitoDTO1).isNotEqualTo(origemDaReservaDeLeitoDTO2);
    }
}
