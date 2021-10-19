package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class EventoLeitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoLeito.class);
        EventoLeito eventoLeito1 = new EventoLeito();
        eventoLeito1.setId(1L);
        EventoLeito eventoLeito2 = new EventoLeito();
        eventoLeito2.setId(eventoLeito1.getId());
        assertThat(eventoLeito1).isEqualTo(eventoLeito2);
        eventoLeito2.setId(2L);
        assertThat(eventoLeito1).isNotEqualTo(eventoLeito2);
        eventoLeito1.setId(null);
        assertThat(eventoLeito1).isNotEqualTo(eventoLeito2);
    }
}
