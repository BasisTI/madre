package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class HorarioExameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioExame.class);
        HorarioExame horarioExame1 = new HorarioExame();
        horarioExame1.setId(1L);
        HorarioExame horarioExame2 = new HorarioExame();
        horarioExame2.setId(horarioExame1.getId());
        assertThat(horarioExame1).isEqualTo(horarioExame2);
        horarioExame2.setId(2L);
        assertThat(horarioExame1).isNotEqualTo(horarioExame2);
        horarioExame1.setId(null);
        assertThat(horarioExame1).isNotEqualTo(horarioExame2);
    }
}
