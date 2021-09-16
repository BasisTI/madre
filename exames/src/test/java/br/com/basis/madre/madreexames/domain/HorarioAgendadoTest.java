package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class HorarioAgendadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioAgendado.class);
        HorarioAgendado horarioAgendado1 = new HorarioAgendado();
        horarioAgendado1.setId(1L);
        HorarioAgendado horarioAgendado2 = new HorarioAgendado();
        horarioAgendado2.setId(horarioAgendado1.getId());
        assertThat(horarioAgendado1).isEqualTo(horarioAgendado2);
        horarioAgendado2.setId(2L);
        assertThat(horarioAgendado1).isNotEqualTo(horarioAgendado2);
        horarioAgendado1.setId(null);
        assertThat(horarioAgendado1).isNotEqualTo(horarioAgendado2);
    }
}
