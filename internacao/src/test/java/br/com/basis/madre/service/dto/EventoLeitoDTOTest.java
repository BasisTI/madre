package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class EventoLeitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoLeitoDTO.class);
        EventoLeitoDTO eventoLeitoDTO1 = new EventoLeitoDTO();
        eventoLeitoDTO1.setId(1L);
        EventoLeitoDTO eventoLeitoDTO2 = new EventoLeitoDTO();
        assertThat(eventoLeitoDTO1).isNotEqualTo(eventoLeitoDTO2);
        eventoLeitoDTO2.setId(eventoLeitoDTO1.getId());
        assertThat(eventoLeitoDTO1).isEqualTo(eventoLeitoDTO2);
        eventoLeitoDTO2.setId(2L);
        assertThat(eventoLeitoDTO1).isNotEqualTo(eventoLeitoDTO2);
        eventoLeitoDTO1.setId(null);
        assertThat(eventoLeitoDTO1).isNotEqualTo(eventoLeitoDTO2);
    }
}
